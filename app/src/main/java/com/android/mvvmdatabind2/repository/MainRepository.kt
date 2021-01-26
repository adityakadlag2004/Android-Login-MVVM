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


class MainRepository(private var context: Context) : BaseRepository(context) {
    private var mAuth = FirebaseAuth.getInstance()


     fun signOut() {
        mAuth = FirebaseAuth.getInstance()

        mAuth.signOut()
        Intent(context, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(it)
        }

    }

}