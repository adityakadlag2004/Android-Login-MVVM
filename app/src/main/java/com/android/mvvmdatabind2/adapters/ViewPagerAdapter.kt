package com.android.mvvmdatabind2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    private var fragments = arrayListOf<Fragment>()


    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    public fun addFragment(fragment:Fragment){
    fragments.add(fragment)
    }
}
