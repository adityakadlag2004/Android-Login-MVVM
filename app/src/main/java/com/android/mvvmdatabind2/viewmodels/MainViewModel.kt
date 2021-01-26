package com.android.mvvmdatabind2.viewmodels

import androidx.lifecycle.ViewModel
import com.android.mvvmdatabind2.repository.MainRepository


class MainViewModel constructor(var repository: MainRepository) : ViewModel() {

    fun signOut() {
            repository.signOut()
    }
}