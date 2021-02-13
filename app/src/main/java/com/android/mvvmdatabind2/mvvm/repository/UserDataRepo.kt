package com.android.mvvmdatabind2.mvvm.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.mvvmdatabind2.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserDataRepo(var context: Context) : BaseRepository(context) {
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    private var storage = FirebaseStorage.getInstance()
    private var storageRef: StorageReference = storage.getReference(Constants.USERS)
    private var currentuser: FirebaseUser? = null


    fun uploadToFirebase(uri: Uri) {
        currentuser = mAuth.currentUser
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
                .addOnFailureListener { Log.d(TAG, "uploadToFirebase: Failed Uploading") }
        }
    }


}