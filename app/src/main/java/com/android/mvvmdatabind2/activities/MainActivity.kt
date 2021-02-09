package com.android.mvvmdatabind2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.databinding.ActivityMainBinding
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.MainRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.MainViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mAuth: FirebaseAuth
    private var currentuser: FirebaseUser? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var component: DaggerFactoryComponent
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mAuth = FirebaseAuth.getInstance()
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(MainRepository(this)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this, component.getFactory())
            .get(MainViewModel::class.java)
        setSupportActionBar(toolBar_main)







        binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                this.lifecycleOwner = this@MainActivity
                this.viewModel = viewModel
            }

        actionBarToggle = ActionBarDrawerToggle(
            this,
            drawer_layout_Main,
            toolBar_main,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        actionBarToggle.syncState()

        drawer_layout_Main.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        actionBarToggle.isDrawerIndicatorEnabled=true
        actionBarToggle.isDrawerSlideAnimationEnabled=true
        nav_Main.setNavigationItemSelectedListener(this)

    }


    override fun onStart() {
        super.onStart()
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        if (currentuser == null) {
            Intent(this, IntroActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        } else {
            Log.d(TAG, "onStart: ${currentuser!!.email.toString()}")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_account -> {
                return true
            }
            R.id.menu_logout -> {
                viewModel.signOut()
                return true
            }
            R.id.menu_active_membership -> {
                return true
            }
            R.id.menu_set -> {
                return true
            }

            R.id.menu_history -> {
                return true
            }
            else -> {
                return false
            }
        }
    }



    override fun onBackPressed() {
        if (drawer_layout_Main.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_Main.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
