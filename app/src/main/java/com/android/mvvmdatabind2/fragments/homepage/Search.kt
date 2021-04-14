package com.android.mvvmdatabind2.fragments.homepage

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.others.models.Membership
import com.android.mvvmdatabind2.others.models.MembershipBusiness
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class Search : Fragment(), View.OnClickListener {

    var memAdapter = ArrayList<MembershipBusiness>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.cardGym.setOnClickListener(this)
        view.cardYoga.setOnClickListener(this)
        view.cardSwimming.setOnClickListener(this)
        view.cardMeditation.setOnClickListener(this)
        view.cardHealthClubs.setOnClickListener(this)
        view.cardHotStar.setOnClickListener(this)
        view.cardNetflix.setOnClickListener(this)
        view.cardPrime.setOnClickListener(this)
        view.cardZumba.setOnClickListener(this)
        view.cardotherEntertainment.setOnClickListener(this)
        view.cardTuition.setOnClickListener(this)
        view.carjeeIIt.setOnClickListener(this)


        view.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//

                return false
            }

        })
        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.cardGym -> {
            }
            R.id.cardYoga -> {
            }
            R.id.cardSwimming -> {
            }
            R.id.cardMeditation -> {
            }
            R.id.cardHealthClubs -> {
            }
            R.id.cardHotStar -> {
            }
            R.id.cardNetflix -> {
            }
            R.id.cardPrime -> {
            }
            R.id.cardZumba -> {
            }
            R.id.cardotherEntertainment -> {
            }
            R.id.cardTuition -> {
            }
            R.id.carjeeIIt -> {
            }
        }

    }


}