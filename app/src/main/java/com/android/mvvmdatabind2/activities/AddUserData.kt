package com.android.mvvmdatabind2.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.Constants.DEFAULT_IMAGE_PROFILE
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
    var myRef = database.getReference(USERS)
    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var username: String
    var imageUri: Uri? = null
    private lateinit var profileImg: String
    var storage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.getReference(USERS)
    private var currentuser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_data)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser

        change_photo.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
                startActivityForResult(galleryIntent, 2)
            }


        }

        btn_continue_data.setOnClickListener {
            if (imageUri != null) {
                uploadToFirebase(imageUri!!)
            }
        }


    }



    private fun uploadToFirebase(uri: Uri) {
        if (currentuser != null) {
            val fileReference: StorageReference = storageRef.child(currentuser!!.uid)
                .child(USER_PROFILE_IMAGE)

            fileReference.putFile(uri)
                .addOnSuccessListener {
                    fileReference.downloadUrl.addOnSuccessListener {
                        myRef.child(currentuser!!.uid).child(USER_PROFILE_IMAGE)
                            .setValue(it.toString())
                        sendUserToMainActivity()
                    }
                }
                .addOnProgressListener { }
                .addOnFailureListener { TODO("Not yet implemented") }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            profileImage_Data.setImageURI(imageUri)
        }
    }

    private fun sendUserToMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}