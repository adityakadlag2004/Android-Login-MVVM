package com.android.mvvmdatabind2.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mvvmdatabind2.mvvm.repository.AuthRepository
import com.android.mvvmdatabind2.mvvm.repository.BaseRepository
import com.android.mvvmdatabind2.mvvm.repository.MainRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.AuthViewModel
import com.android.mvvmdatabind2.mvvm.viewmodels.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository as AuthRepository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository = repository as MainRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}