package com.syeddev.medialibraryapp.di

import com.google.firebase.auth.FirebaseAuth
import com.syeddev.medialibraryapp.core.manager.FirebaseAuthManager
import com.syeddev.medialibraryapp.core.manager.FirebaseAuthManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthManager(firebaseAuth: FirebaseAuth): FirebaseAuthManager = FirebaseAuthManagerImpl(firebaseAuth)
}