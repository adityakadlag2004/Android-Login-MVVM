package com.android.mvvmdatabind2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.auth.LoginActivity
import com.android.mvvmdatabind2.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mAuth: FirebaseAuth
    private var currentuser: FirebaseUser? = null
    private val repository=AuthRepository(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mAuth = FirebaseAuth.getInstance()

        repository.emailFlow.asLiveData().observe(this, Observer {
            email_id.text = it.toString()

        })


        repository.emailverified.asLiveData().observe(this, Observer {
            display_name.text = it.toString()
            if (it == "NO") {
                mAuth.currentUser?.delete()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Intent(this, LoginActivity::class.java).also {
                            it.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(it)
                        }
                        finish()
                    }
                }
            } else {
                Log.d(TAG, "onCreate: User is Verified")
            }
        })

        hello.setOnClickListener {
            mAuth.signOut()
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        if (currentuser == null) {
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        } else {
            Log.d(TAG, "onStart: ${currentuser!!.email.toString()}")

            //Toast.makeText(this, currentuser!!.email.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
