package com.android.mvvmdatabind2.activities.Userdata

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfile : AppCompatActivity() {
    private var phoneNumber: String=""
    var imageUri: Uri? = null
    private var currentuser: FirebaseUser? = null
    private var username: String=""
    private var profileImg: String=""
    var database = FirebaseDatabase.getInstance()
    private val TAG = "EditProfile"
    var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        getData()
    }

    fun getData() {
        if (currentuser != null) {
            myRef.child(currentuser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        username = snapshot.child(Constants.USER_NAME).value.toString()
                        profileImg = snapshot.child(Constants.USER_PROFILE_IMAGE).value.toString()
                        phoneNumber = snapshot.child(Constants.USER_PHONENUMBER).value.toString()

                        addName_data_EditProfile.setText(username)

                        Picasso.get().load(profileImg.toUri()).into(profileImage_EditProfile)

                        addPhone_data_EditProfile.setText(phoneNumber)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: ${error.message}")
                }
            })
        }
    }
}