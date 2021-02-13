package com.android.mvvmdatabind2.mvvm.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.mvvmdatabind2.mvvm.repository.BaseRepository
import com.android.mvvmdatabind2.mvvm.repository.UserDataRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserDataViewModel(var repository:UserDataRepo) :ViewModel() {

    fun uploadToFirebase(uri: Uri){
      CoroutineScope(IO).launch {
          repository.uploadToFirebase(uri)
      }
    }
    fun sendUserToMainActivity(){
        repository.sendUserToMainActivity()
    }


}