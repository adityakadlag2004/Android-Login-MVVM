package com.android.mvvmdatabind2.di.modules

import com.android.mvvmdatabind2.di.factory.ModelFactory
import com.android.mvvmdatabind2.mvvm.repository.BaseRepository
import dagger.Module
import dagger.Provides

@Module
class FactoryModule constructor(var authRepository: BaseRepository) {

    @Provides
    fun provideModalFactory(): ModelFactory {
        return ModelFactory(authRepository)
    }

    @Provides
    fun providesRepository():BaseRepository{
        return authRepository
    }
}