package com.android.mvvmdatabind2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.fragments.userinfo.CollectData
import com.android.mvvmdatabind2.others.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_get_user_info.*
import kotlinx.android.synthetic.main.activity_intro.*

class GetUserInfo : AppCompatActivity() {
    private var collectDataFragment = CollectData()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user_info)


        tabLayoutUserInfo.setupWithViewPager(viewPagerUserInfo)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(collectDataFragment)
        viewPagerUserInfo.adapter = viewPagerAdapter
    }
}