package com.android.mvvmdatabind2.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import com.android.mvvmdatabind2.mvvm.repository.MainRepository


class MainViewModel constructor(var repository: MainRepository) : ViewModel() {

    fun signOut() {
            repository.signOut()
    }
}