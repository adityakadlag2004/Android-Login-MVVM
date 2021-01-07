package com.android.mvvmdatabind2.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.mvvmdatabind2.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(private var context: Context) {
    private var mAuth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Intent(context, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(it)
                    }
                } else {
                    Log.d(TAG, "login: Login Failed :- ${it.exception}")
                }
            }
    }

    suspend fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Intent(context, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(it)
                    }
                } else {
                    Log.d(TAG, "login: Login Failed :- ${task.exception}")
                }
            }
    }
}