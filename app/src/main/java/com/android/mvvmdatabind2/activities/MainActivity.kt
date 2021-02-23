package com.android.mvvmdatabind2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.fragments.homepage.Home
import com.android.mvvmdatabind2.fragments.homepage.Profile
import com.android.mvvmdatabind2.fragments.homepage.Search
import com.android.mvvmdatabind2.mvvm.repository.MainRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    private lateinit var component: DaggerFactoryComponent
    val homeFragment = Home()
    val serachFragment = Search()
    val profileFragment = Profile()
    private val TAG = "MainActivity"
    private var currentuser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        viewModel.checkUserHasData().observe(this, {
            if (it == "no") {
                viewModel.sendUsertoAddUserDataActivity()
            }
        })

        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)


                R.id.miProfile -> setCurrentFragment(profileFragment)


                R.id.miSearch -> setCurrentFragment(serachFragment)



            }
            true
        }


    }

    private fun init() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(MainRepository(this)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this, component.getFactory())
            .get(MainViewModel::class.java)

        setCurrentFragment(homeFragment)

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FrameMain, fragment)
            commit()
        }


}
