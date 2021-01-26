package com.android.mvvmdatabind2.mvvm.repository

import android.content.Context
import android.content.Intent
import com.android.mvvmdatabind2.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth


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