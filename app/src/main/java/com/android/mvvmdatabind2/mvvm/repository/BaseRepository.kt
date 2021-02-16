package com.android.mvvmdatabind2.mvvm.repository

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.activities.Userdata.AddUserData
import com.android.mvvmdatabind2.activities.Userdata.EditProfile
import com.android.mvvmdatabind2.activities.auth.LoginActivity
import com.android.mvvmdatabind2.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

abstract class BaseRepository(var contextBase: Context) {
    private var mAuthBase = FirebaseAuth.getInstance()
    var databaseBase = FirebaseDatabase.getInstance()
    var myRefBase = databaseBase.getReference(Constants.USERS)
    var username2Base = MutableLiveData<String>()
    var emailBase = MutableLiveData<String>()
    var profileImageBase = MutableLiveData<String>()
    var userdataBase = MutableLiveData<String>()
    var count2 = MutableLiveData<String>()
    var curUser=mAuthBase.currentUser
    fun signOut() {
        mAuthBase = FirebaseAuth.getInstance()

        mAuthBase.signOut()
        Intent(contextBase, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            contextBase.startActivity(it)

        }

    }

    fun checkUserHasData(): MutableLiveData<String> {
        val user = mAuthBase.currentUser
        if (user != null) {
            myRefBase.child(user.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(Constants.USER_PHONENUMBER) && snapshot.hasChild(Constants.USER_PROFILE_IMAGE)) {
                        userdataBase.value = "yes"
                    } else {
                        userdataBase.value = "no"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(ContentValues.TAG, "onCancelled: ${error.message}")
                }
            })
        }
        return userdataBase
    }

    fun sendUserToAddUserData() {
        Intent(contextBase, AddUserData::class.java).also {
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            contextBase.startActivity(it)

        }
    }

    fun getUsername(): MutableLiveData<String> {
        val user = mAuthBase.currentUser
        if (user != null && username2Base.value.isNullOrEmpty()) {
            myRefBase.child(mAuthBase.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            username2Base.value =
                                snapshot.child(Constants.USER_NAME).value.toString()

                            Log.d(ContentValues.TAG, "onDataChange: Repo$username2Base")
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(ContentValues.TAG, "onCancelled: Fail")
                    }
                })
        }

        Log.d(ContentValues.TAG, "onDataChange: Last Repo$username2Base ")
        return username2Base
    }


    fun getEmail(): MutableLiveData<String> {
        val user = mAuthBase.currentUser
        if (user != null && username2Base.value.isNullOrEmpty()) {
            myRefBase.child(mAuthBase.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            emailBase.value =
                                snapshot.child(Constants.USER_EMAIL).value.toString()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(ContentValues.TAG, "onCancelled: Fail")
                    }
                })
        }

        Log.d(ContentValues.TAG, "onDataChange: Last Repo$emailBase ")
        return emailBase
    }

    fun getImage(): MutableLiveData<String> {
        val user = mAuthBase.currentUser
        if (user != null && username2Base.value.isNullOrEmpty()) {
            myRefBase.child(mAuthBase.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            profileImageBase.value =
                                snapshot.child(Constants.USER_PROFILE_IMAGE).value.toString()
                            Log.d(ContentValues.TAG, "onDataChange: Repo$username2Base")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(ContentValues.TAG, "onCancelled: Fail")
                    }
                })
        }

        Log.d(ContentValues.TAG, "onDataChange: Last Repo$username2Base ")
        return profileImageBase
    }

    fun sendUserToMainActivity() {
        Intent(contextBase, MainActivity::class.java).also {
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            contextBase.startActivity(it)
        }
    }

    fun sendUserToEditProfile() {
        Intent(contextBase, EditProfile::class.java).also {
            contextBase.startActivity(it)
        }
    }

    fun updateUser(username: String?=null, contactnumber: String?=null) {
    curUser=mAuthBase.currentUser
    if (curUser!=null)
    {
        if (!username.isNullOrEmpty())
        myRefBase.child(curUser!!.uid).child(Constants.USER_NAME).setValue(username)


        if (!contactnumber.isNullOrEmpty())
        myRefBase.child(curUser!!.uid).child(Constants.USER_PHONENUMBER).setValue(contactnumber)
    }
    }


    fun getMembershipCount(): MutableLiveData<String> {
        val user = mAuthBase.currentUser
        if (user != null && username2Base.value.isNullOrEmpty()) {
            myRefBase.child(mAuthBase.currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            count2.value = snapshot.child(Constants.USER_MEMBERSHIP_COUNT).value.toString()

                            Log.d(ContentValues.TAG, "onDataChange: Repo$username2Base")
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(ContentValues.TAG, "onCancelled: Fail")
                    }
                })
        }

        Log.d(ContentValues.TAG, "onDataChange: Last Repo$count2 ")
        return count2
    }
}