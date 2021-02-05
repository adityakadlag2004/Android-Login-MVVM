package com.android.mvvmdatabind2.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.activities.MainActivity
import com.android.mvvmdatabind2.databinding.ActivityLoginBinding
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.AuthRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.AuthViewModel
import com.android.mvvmdatabind2.mvvm.factory.ModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var factory: ModelFactory
    private val TAG = "LoginActivity"
    private lateinit var mAuth: FirebaseAuth
    private var currentuser: FirebaseUser? = null
    private var verifiedboolean = false
    private lateinit var component: DaggerFactoryComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()



        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(AuthRepository(this)))
            .build() as DaggerFactoryComponent


        viewModel =
            ViewModelProviders.of(this, component.getFactory()).get(AuthViewModel::class.java)

        val binding = DataBindingUtil
            .setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
            .apply {
                this.lifecycleOwner = this@LoginActivity
                this.viewmodel = viewModel
            }

        binding.txtLog.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also { startActivity(it) }
        }

    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        if (currentuser != null) {
            verifiedboolean = currentuser!!.isEmailVerified
            if (verifiedboolean) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        } else {
            Log.d(TAG, "onStart:Not Verified ")
        }
    }
}