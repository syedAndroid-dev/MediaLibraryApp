package com.syeddev.medialibraryapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.syeddev.medialibraryapp.core.manager.FirebaseAuthManager
import com.syeddev.medialibraryapp.core.manager.FirebaseAuthManagerImpl
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManager
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManagerImpl
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
    fun provideFirebaseStorage() : FirebaseStorage = Firebase.storage


    @Provides
    @Singleton
    fun provideFirebaseFireStore() : FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseAuthManager(firebaseAuth: FirebaseAuth): FirebaseAuthManager = FirebaseAuthManagerImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideFirebaseStorageManager(firebaseStorage: FirebaseStorage,firestore: FirebaseFirestore): FirebaseStorageManager {
        return FirebaseStorageManagerImpl(firebaseStorage,firestore)
    }
}