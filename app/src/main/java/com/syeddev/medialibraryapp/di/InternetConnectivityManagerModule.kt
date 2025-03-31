package com.syeddev.medialibraryapp.di

import android.content.Context
import com.syeddev.medialibraryapp.core.manager.InternetConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InternetConnectivityManagerModule {

    @Provides
    @Singleton
    fun provideInternetConnectivityManager(@ApplicationContext appContext: Context): InternetConnectivityManager{
        return InternetConnectivityManager(context = appContext)
    }
}