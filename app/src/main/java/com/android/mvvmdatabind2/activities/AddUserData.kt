package com.android.mvvmdatabind2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AddUserData : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    private var currentuser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_data)
        mAuth= FirebaseAuth.getInstance()
        currentuser=mAuth.currentUser

    }
}