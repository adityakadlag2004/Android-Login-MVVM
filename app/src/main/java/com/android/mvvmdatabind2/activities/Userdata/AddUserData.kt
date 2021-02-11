package com.android.mvvmdatabind2.activities.Userdata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.UserDataRepo
import com.android.mvvmdatabind2.mvvm.viewmodels.UserDataViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_user_data.*


class AddUserData : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    var imageUri: Uri? = null
    private lateinit var component: DaggerFactoryComponent
    private var currentuser: FirebaseUser? = null
    private lateinit var viewModel: UserDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user_data)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser

        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(UserDataRepo(this)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this,component.getFactory()).get(UserDataViewModel::class.java)

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
                viewModel.uploadToFirebase(imageUri!!)
                progress_bar_data.visibility= View.VISIBLE
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            profileImage_Data.setImageURI(imageUri)
        }
    }


}