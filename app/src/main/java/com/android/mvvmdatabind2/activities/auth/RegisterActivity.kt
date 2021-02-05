package com.android.mvvmdatabind2.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.databinding.ActivityRegisterBinding
import com.android.mvvmdatabind2.mvvm.repository.AuthRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.AuthViewModel
import com.android.mvvmdatabind2.mvvm.factory.ModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: AuthViewModel
    private lateinit var factory: ModelFactory
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "RegisterActivity"
    private var verifiedboolean=false
    private var currentuser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()

        authRepository= AuthRepository(this)
        factory= ModelFactory(authRepository)
        viewModel= ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

        val binding=DataBindingUtil
            .setContentView<ActivityRegisterBinding>(this,R.layout.activity_register)
            .apply {
                this.lifecycleOwner = this@RegisterActivity
                this.viewmodel=viewModel
            }

        binding.txtReg.setOnClickListener {
            Intent(this, LoginActivity::class.java).also { startActivity(it) }
        }
    }
    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        if (currentuser!=null) {
            verifiedboolean=currentuser!!.isEmailVerified
            if (verifiedboolean) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        }
        else{
            Log.d(TAG, "onStart:Not Verified ")
        }
    }
}