package com.android.mvvmdatabind2.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.auth.LoginActivity
import com.android.mvvmdatabind2.repository.AuthRepository
import com.android.mvvmdatabind2.retrofit.ServiceBuilder
import com.android.mvvmdatabind2.retrofit.User
import com.android.mvvmdatabind2.retrofit.UserAdapter
import com.android.mvvmdatabind2.retrofit.UsersService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        loadUsers()

    }

    private fun loadUsers() {
        val userService = ServiceBuilder.buildService(UsersService::class.java)

        val requestCall = userService.getUsers()

        requestCall.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val list = response.body()!!
                    Log.d(TAG, "onResponse: $list")
                    val adapter = UserAdapter(list)

                    recyclerviewMain.adapter = adapter
                    recyclerviewMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
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
        }
    }

}
