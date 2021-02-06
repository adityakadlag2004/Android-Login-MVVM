package com.android.mvvmdatabind2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.activities.auth.LoginActivity
import com.android.mvvmdatabind2.activities.auth.RegisterActivity
import com.android.mvvmdatabind2.fragments.FragFirst
import com.android.mvvmdatabind2.fragments.frag2
import com.android.mvvmdatabind2.others.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private val frag1 = FragFirst()
    private val frag2 = frag2()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btn_intro_signIn.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        btn_intro_SignUp.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        tabLayout.setupWithViewPager(viewPagerIntro as ViewPager)

        viewPagerAdapter=ViewPagerAdapter(supportFragmentManager,0)
        viewPagerAdapter.addFragment(frag1)
        viewPagerAdapter.addFragment(frag2)
        viewPagerIntro.adapter = viewPagerAdapter



    }
}