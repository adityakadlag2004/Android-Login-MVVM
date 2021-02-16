package com.android.mvvmdatabind2.fragments.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.MainRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*


class Profile : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    private lateinit var component: DaggerFactoryComponent
    private var currentuser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mAuth = FirebaseAuth.getInstance()
        val email = view.findViewById<TextView>(R.id.emailFrag)
        currentuser = mAuth.currentUser
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(view.context))
            .factoryModule(FactoryModule(MainRepository(view.context)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this, component.getFactory())
            .get(MainViewModel::class.java)
//        if (currentuser!=null)
//        {emailFrag.text= currentuser!!.email}
        viewModel.getUsername().observe(viewLifecycleOwner, {
            usernameFrag.text = it.toString()
        })
        viewModel.getEmail().observe(viewLifecycleOwner, {
            emailFrag.text = it.toString()
        })

        viewModel.getImage().observe(viewLifecycleOwner, {
            Picasso.get().load(it.toUri()).into(profileImageFrag)
        })
        viewModel.getMemberShipCount().observe(viewLifecycleOwner, {
            memCount.text = it.toString()
        })


        return view
    }

}