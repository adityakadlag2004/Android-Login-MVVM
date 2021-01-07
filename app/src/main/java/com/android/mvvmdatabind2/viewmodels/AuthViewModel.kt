package com.android.mvvmdatabind2.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mvvmdatabind2.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) :ViewModel() {

    val email:String?=null

    val password:String?=null

    fun login() = CoroutineScope(IO).launch {
        repository.login(email.toString(),password.toString())
    }

    fun register() = CoroutineScope(IO).launch {
        repository.register(email.toString(),password.toString())
    }
}