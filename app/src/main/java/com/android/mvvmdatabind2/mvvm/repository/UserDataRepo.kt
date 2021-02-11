package com.android.mvvmdatabind2.mvvm.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserDataRepo(var context: Context) : BaseRepository(context) {
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var username: String
    var imageUri: Uri? = null
    private lateinit var profileImg: String
    var storage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.getReference(Constants.USERS)
    private var currentuser: FirebaseUser? = null



     suspend fun uploadToFirebase(uri: Uri) {
        currentuser=mAuth.currentUser
        if (currentuser != null) {
            val fileReference: StorageReference = storageRef.child(currentuser!!.uid)
                .child(Constants.USER_PROFILE_IMAGE)

            fileReference.putFile(uri)
                .addOnSuccessListener {
                    fileReference.downloadUrl.addOnSuccessListener {
                        myRef.child(currentuser!!.uid).child(Constants.USER_PROFILE_IMAGE)
                            .setValue(it.toString())
                        sendUserToMainActivity()
                    }
                }
                .addOnProgressListener {

                }
                .addOnFailureListener { TODO("Not yet implemented") }
        }
    }

     fun sendUserToMainActivity() {
        Intent(context, MainActivity::class.java).also {
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(it)
        }
    }
}