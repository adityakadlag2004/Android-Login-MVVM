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









}

