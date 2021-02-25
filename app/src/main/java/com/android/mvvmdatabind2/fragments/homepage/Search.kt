package com.android.mvvmdatabind2.fragments.homepage

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.mvvmdatabind2.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class Search : Fragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardGym.setOnClickListener(this)
        cardYoga.setOnClickListener(this)
        cardSwimming.setOnClickListener(this)
        cardMeditation.setOnClickListener(this)
        cardHealthClubs.setOnClickListener(this)
        cardHotStar.setOnClickListener(this)
        cardNetflix.setOnClickListener(this)
        cardPrime.setOnClickListener(this)
        cardZumba.setOnClickListener(this)
        cardotherEntertainment.setOnClickListener(this)
        cardTuition.setOnClickListener(this)
        carjeeIIt.setOnClickListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)


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