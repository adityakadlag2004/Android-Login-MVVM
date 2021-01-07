package com.android.mvvmdatabind2.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.databinding.ActivityLoginBinding
import com.android.mvvmdatabind2.databinding.ActivityRegisterBinding
import com.android.mvvmdatabind2.repository.AuthRepository
import com.android.mvvmdatabind2.viewmodels.AuthViewModel
import com.android.mvvmdatabind2.viewmodels.factory.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: AuthViewModel
    private lateinit var factory:AuthViewModelFactory
    private lateinit var mAuth: FirebaseAuth
    private var currentuser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        authRepository= AuthRepository(this)
        factory= AuthViewModelFactory(authRepository)
        viewModel= ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

        val binding=DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
            .apply {
                this.setLifecycleOwner(this@LoginActivity)
                this.viewModel=viewModel
            }

        binding.txtLog.setOnClickListener {
            Intent(this,RegisterActivity::class.java).also { startActivity(it) }
        }

    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        if (currentuser != null) {
            Intent(this, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }
    }
}