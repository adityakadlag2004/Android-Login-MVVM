package com.android.mvvmdatabind2.di

import android.content.Context
import com.android.mvvmdatabind2.repository.AuthRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule constructor(var context: Context){

    @Provides
    fun provideRepository():AuthRepository{
       return AuthRepository(context = context)
    }
}