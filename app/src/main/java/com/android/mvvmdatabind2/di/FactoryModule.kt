package com.android.mvvmdatabind2.di

import com.android.mvvmdatabind2.repository.AuthRepository
import com.android.mvvmdatabind2.viewmodels.factory.ModelFactory
import dagger.Module
import dagger.Provides

@Module
class FactoryModule constructor(var authRepository: AuthRepository) {

    @Provides
    fun provideModalFactory(): ModelFactory {
        return ModelFactory(authRepository)
    }
}