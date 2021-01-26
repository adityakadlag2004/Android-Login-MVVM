package com.android.mvvmdatabind2.di

import com.android.mvvmdatabind2.viewmodels.factory.ModelFactory
import dagger.Component

@Component(modules = [FactoryModule::class,RepositoryModule::class])
interface AuthComponent {

    fun getFactory():ModelFactory
}