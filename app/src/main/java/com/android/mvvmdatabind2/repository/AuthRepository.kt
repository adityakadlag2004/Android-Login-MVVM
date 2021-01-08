package com.android.mvvmdatabind2.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.android.mvvmdatabind2.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class AuthRepository(private var context: Context) {
    private var mAuth = FirebaseAuth.getInstance()

    companion object{
        val EMAIL_KEY= preferencesKey<String>("EMAIL")

        val DISPLAY_NAME= preferencesKey<String>("NAME")
    }
    private val dataStore=context.createDataStore("user_info")
     fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
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



     fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user=mAuth.currentUser
                    CoroutineScope(IO).launch {
                        if (user != null) {
                            storeData(user,email)
                        }
                    }
                    Intent(context, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(it)
                    }
                } else {
                    Log.d(TAG, "login: Login Failed :- ${task.exception}")
                }
            }
    }

    suspend fun storeData(user: FirebaseUser, email: String)
    {
        dataStore.edit {
            it[EMAIL_KEY]= email
            it[DISPLAY_NAME]= user.displayName.toString()
        }
    }

    val emailFlow:Flow<String> = dataStore.data.map {
        it[EMAIL_KEY]?:""
    }
    val displayname:Flow<String> = dataStore.data.map {
        it[DISPLAY_NAME]?:""
    }

}