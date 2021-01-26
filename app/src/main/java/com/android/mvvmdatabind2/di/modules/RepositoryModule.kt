package com.android.mvvmdatabind2.di.modules

import android.content.Context
import com.android.mvvmdatabind2.mvvm.repository.AuthRepository
import com.android.mvvmdatabind2.mvvm.repository.BaseRepository
import com.android.mvvmdatabind2.mvvm.repository.MainRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule constructor(var context: Context){

    @Provides
    fun provideRepository(): BaseRepository {
       return AuthRepository(context = context)
    }
    @Provides
    fun provideMainRepository(): BaseRepository {
        return MainRepository(context = context)
    }

}