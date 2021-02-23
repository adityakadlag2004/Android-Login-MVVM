package com.android.mvvmdatabind2.mvvm.viewmodels

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mvvmdatabind2.mvvm.repository.BaseRepository
import com.android.mvvmdatabind2.mvvm.repository.UserDataRepo
import com.android.mvvmdatabind2.others.models.Membership
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserDataViewModel(var repository:UserDataRepo) :ViewModel() {

    var userMemberShips = MutableLiveData<ArrayList<Membership>>()
    fun uploadToFirebase(uri: Uri){
      CoroutineScope(IO).launch {
          repository.uploadToFirebase(uri)
      }
    }
    fun sendUserToMainActivity(){
        repository.sendUserToMainActivity()
    }

    fun updateUser(username:String?=null,contactnumber:String?=null)
    {
       repository.updateUser(username,contactnumber)
    }

    fun getUsername(): LiveData<String> {
        val _username=repository.getUsername()
        Log.d(ContentValues.TAG, "onDataChange: viewmodel ${_username.value} ")
        return _username
    }

    fun getImage(): LiveData<String> {
        return repository.getImage()
    }

    fun getEmail(): LiveData<String> {
        return repository.getEmail()
    }
    fun getMemberShipCount(): LiveData<String> {
        return repository.getMembershipCount()
    }

    fun getMemberships():LiveData<ArrayList<Membership>> {
        return userMemberShips
    }


}