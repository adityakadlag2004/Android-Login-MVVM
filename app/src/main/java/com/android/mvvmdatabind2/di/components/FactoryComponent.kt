package com.android.mvvmdatabind2.di.components


import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.factory.ModelFactory
import dagger.Component

@Component(modules = [FactoryModule::class, RepositoryModule::class])
interface FactoryComponent {

    fun getFactory(): ModelFactory




}