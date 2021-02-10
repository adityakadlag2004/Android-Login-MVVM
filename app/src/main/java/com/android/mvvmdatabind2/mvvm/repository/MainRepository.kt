package com.android.mvvmdatabind2.mvvm.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.mvvmdatabind2.activities.auth.LoginActivity
import com.android.mvvmdatabind2.others.Constants
import com.android.mvvmdatabind2.others.Constants.USER_NAME
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


    fun signOut() {
        mAuth = FirebaseAuth.getInstance()

        mAuth.signOut()
        Intent(context, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
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

}