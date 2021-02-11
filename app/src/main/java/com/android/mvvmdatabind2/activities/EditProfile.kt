package com.android.mvvmdatabind2.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfile : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()
    private lateinit var username: String
    var imageUri: Uri? = null
    private var currentuser: FirebaseUser? = null
    private lateinit var profileImg: String
    var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
    }

    fun getData() {
        if (currentuser != null) {
            myRef.child(currentuser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        username = snapshot.child(Constants.USER_NAME).value.toString()
                        profileImg = snapshot.child(Constants.USER_PROFILE_IMAGE).value.toString()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}