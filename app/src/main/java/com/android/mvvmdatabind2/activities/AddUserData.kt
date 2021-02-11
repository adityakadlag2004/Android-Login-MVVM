package com.android.mvvmdatabind2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.Constants
import com.android.mvvmdatabind2.others.Constants.USERS
import com.android.mvvmdatabind2.others.Constants.USER_NAME
import com.android.mvvmdatabind2.others.Constants.USER_PROFILE_IMAGE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_user_data.*


class AddUserData : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var username: String
    private lateinit var profileImg: String
    var storage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.getReference(USERS)
    private var currentuser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_data)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser

        getUserData()
        change_photo.setOnClickListener {
            val galleryIntent: Intent = Intent()
            galleryIntent.apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
                startActivityForResult(galleryIntent, 2)
            }


        }

        btn_continue_data.setOnClickListener {

        }


    }

    private fun getUserData() {
        if (currentuser != null) {
            myRef.child(currentuser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        username = snapshot.child(USER_NAME).value.toString()
                        profileImg = snapshot.child(USER_PROFILE_IMAGE).value.toString()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}