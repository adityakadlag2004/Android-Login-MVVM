package com.android.mvvmdatabind2.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface UsersService {

    @GET("api/users?page=2")
    fun getUsers():Call<List<User>>
}