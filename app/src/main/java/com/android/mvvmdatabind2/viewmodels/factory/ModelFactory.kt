package com.android.mvvmdatabind2.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mvvmdatabind2.repository.AuthRepository
import com.android.mvvmdatabind2.viewmodels.AuthViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ModelFactory(
    private val repository: AuthRepository
) :ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java))
        {return AuthViewModel(repository as AuthRepository) as T }
        else {throw IllegalArgumentException("ViewModel Not Found")}
    }

}