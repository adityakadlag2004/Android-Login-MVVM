package com.android.mvvmdatabind2.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class AuthRepository(private var context: Context) {
    private var mAuth = FirebaseAuth.getInstance()

    companion object {
        val EMAIL_KEY = preferencesKey<String>("EMAIL")

        val DISPLAY_NAME = preferencesKey<String>("NAME")

        val EMAIL_VERIFIED_KEY = preferencesKey<String>("VERIFICATION_EMAIL_KEY")
    }

    private val dataStore = context.createDataStore("user_info")

    fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (mAuth.currentUser!!.isEmailVerified) {
                            val user = mAuth.currentUser
                            CoroutineScope(IO).launch {
                                if (user != null) {
                                    storeData(user, email, "YES")
                                }
                            }
                            Intent(context, MainActivity::class.java).also {
                                it.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(it)
                            }
                        } else {
                            val user2 = mAuth.currentUser
                            CoroutineScope(IO).launch {
                                if (user2 != null) {
                                    storeData(user2, email, "NO")
                                }
                            }
                            Toast.makeText(context, "First Verify Your Email", Toast.LENGTH_SHORT)
                                .show()
                        }
                } else {
                    Log.d(TAG, "login: Login Failed :- ${task.exception}")
                }
            }
    }

    fun forgotPassword(email: String, password: String) {
        mAuth.confirmPasswordReset(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "forgotPassword: Password Has Been Reset")
            } else {
                Log.d(TAG, "forgotPassword: ${it.exception?.message}")
            }
        }
    }


    fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        Toast.makeText(
                            context,
                            "Check your Email For Verification",
                            Toast.LENGTH_SHORT
                        ).show()
                        val user = mAuth.currentUser
                            CoroutineScope(IO).launch {
                                if (user != null) {
                                    storeData(user, email, "NO")
                                }
                            }
                        Intent(context, LoginActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }

                } else {
                    Intent(context, LoginActivity::class.java).also {
                        context.startActivity(it)
                    }
                    Log.d(TAG, "login: Login Failed :- ${task.exception}")
                }
            }
    }

    suspend fun storeData(user: FirebaseUser, email: String, emailVerified: String? = null) {
        dataStore.edit {
            it[EMAIL_KEY] = email
            it[DISPLAY_NAME] = user.displayName.toString()
            it[EMAIL_VERIFIED_KEY] = emailVerified ?: "NO"
        }
    }

    val emailFlow: Flow<String> = dataStore.data.map {
        it[EMAIL_KEY] ?: ""
    }
    val displayname: Flow<String> = dataStore.data.map {
        it[DISPLAY_NAME] ?: ""
    }

    val emailverified: Flow<String> = dataStore.data.map {
        it[EMAIL_VERIFIED_KEY] ?: ""
    }

}