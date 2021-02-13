package com.android.mvvmdatabind2.activities.Userdata

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.UserDataRepo
import com.android.mvvmdatabind2.mvvm.viewmodels.UserDataViewModel
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
    private var phoneNumber: String = ""
    var imageUri: Uri? = null
    private var currentuser: FirebaseUser? = null
    private var username: String = ""
    private lateinit var component: DaggerFactoryComponent
    private var profileImg: String = ""
    var database = FirebaseDatabase.getInstance()
    private val TAG = "EditProfile"
    private lateinit var viewModel: UserDataViewModel
    var myRef = database.getReference(Constants.USERS)
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        getData()
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(UserDataRepo(this)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this, component.getFactory())
            .get(UserDataViewModel::class.java)

        change_photo_EditProfile.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
                startActivityForResult(galleryIntent, 2)
            }


        }
        btn_continue_data_EditProfile.setOnClickListener {
            val name = addName_data_EditProfile.text
            val phone = addPhone_data_EditProfile.text

            if (name!!.isNotEmpty() && phone.isNotEmpty()) {
                viewModel.updateUser(name.toString(), phone.toString())
                if (imageUri != null) {
                    viewModel.uploadToFirebase(imageUri as Uri)
                }
                if (imageUri==null)
                {
                    viewModel.sendUserToMainActivity()
                }
            } else {
                Toast.makeText(this, "Fill the Fields", Toast.LENGTH_SHORT).show()
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            profileImage_EditProfile.setImageURI(imageUri)
        }
    }
}