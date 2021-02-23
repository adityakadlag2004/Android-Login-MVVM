package com.android.mvvmdatabind2.fragments.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.adapters.MembershipsAdapter
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.UserDataRepo
import com.android.mvvmdatabind2.mvvm.viewmodels.UserDataViewModel
import com.android.mvvmdatabind2.others.models.Membership
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: UserDataViewModel
    lateinit var adapter:MembershipsAdapter
    private lateinit var component: DaggerFactoryComponent
    private var currentuser: FirebaseUser? = null
    private var memList = ArrayList<Membership>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        adapter= MembershipsAdapter(memList)
        recyclerView_home.adapter=adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        mAuth = FirebaseAuth.getInstance()
        currentuser = mAuth.currentUser
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(view.context))
            .factoryModule(FactoryModule(UserDataRepo(view.context)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this, component.getFactory())
            .get(UserDataViewModel::class.java)

//        viewModel.getMemberships().observe(viewLifecycleOwner,{
//        memList.clear()
//        memList=it
//        adapter.notifyDataSetChanged()
//        })

        return view
    }
}