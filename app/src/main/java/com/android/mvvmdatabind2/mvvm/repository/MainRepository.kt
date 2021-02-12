package com.android.mvvmdatabind2.mvvm.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.mvvmdatabind2.activities.Userdata.AddUserData
import com.android.mvvmdatabind2.activities.auth.LoginActivity
import com.android.mvvmdatabind2.others.Constants
import com.android.mvvmdatabind2.others.Constants.USER_NAME
import com.android.mvvmdatabind2.others.Constants.USER_PHONENUMBER
import com.android.mvvmdatabind2.others.Constants.USER_PROFILE_IMAGE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainRepository(private var context: Context) : BaseRepository(context) {
    private var mAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(Constants.USERS)
    var username2 = MutableLiveData<String>()
    var profileImage = MutableLiveData<String>()
    var userdata = MutableLiveData<String>()


    fun signOut() {
        mAuth = FirebaseAuth.getInstance()

        mAuth.signOut()
        Intent(context, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(it)

        }

    }

    fun getUsername(): MutableLiveData<String> {
        val user = mAuth.currentUser
        if (user != null && username2.value.isNullOrEmpty()) {
            myRef.child(mAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        username2.value = snapshot.child(USER_NAME).value.toString()
                        profileImage.value = snapshot.child(USER_PROFILE_IMAGE).value.toString()
                        Log.d(TAG, "onDataChange: Repo$username2")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: Fail")
                }
            })
        }

        Log.d(TAG, "onDataChange: Last Repo$username2 ")
        return username2
    }

    fun getImage(): MutableLiveData<String> {
        val user = mAuth.currentUser
        if (user != null && username2.value.isNullOrEmpty()) {
            myRef.child(mAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        profileImage.value = snapshot.child(USER_PROFILE_IMAGE).value.toString()
                        Log.d(TAG, "onDataChange: Repo$username2")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: Fail")
                }
            })
        }

        Log.d(TAG, "onDataChange: Last Repo$username2 ")
        return profileImage
    }

    fun checkUserHasData(): MutableLiveData<String> {
        val user = mAuth.currentUser
        if (user != null) {
            myRef.child(user.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(USER_PHONENUMBER) && snapshot.hasChild(USER_PROFILE_IMAGE)) {
                        userdata.value = "yes"
                    } else {
                        userdata.value = "no"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.message}")
                }
            })
        }
        return userdata
    }

    fun sendUserToAddUserData() {
        Intent(context, AddUserData::class.java).also {
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(it)

        }
    }
}

