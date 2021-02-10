package com.android.mvvmdatabind2.mvvm.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mvvmdatabind2.mvvm.repository.MainRepository


class MainViewModel constructor(var repository: MainRepository) : ViewModel() {




    fun signOut() {
        repository.signOut()
    }

    fun getUsername(): LiveData<String> {
        val _username=repository.getUsername()
        Log.d(TAG, "onDataChange: viewmodel ${_username.value} ")
        return _username
    }
}