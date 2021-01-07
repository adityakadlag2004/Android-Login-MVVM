package com.android.mvvmdatabind2.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mvvmdatabind2.repository.AuthRepository
import com.android.mvvmdatabind2.viewmodels.AuthViewModel

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: AuthRepository
) :ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }

}