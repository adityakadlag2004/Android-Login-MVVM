package com.android.mvvmdatabind2.di.modules

import com.android.mvvmdatabind2.repository.AuthRepository
import com.android.mvvmdatabind2.factory.ModelFactory
import com.android.mvvmdatabind2.repository.BaseRepository
import dagger.Module
import dagger.Provides

@Module
class FactoryModule constructor(var authRepository: BaseRepository) {

    @Provides
    fun provideModalFactory(): ModelFactory {
        return ModelFactory(authRepository)
    }
}