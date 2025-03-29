package com.syeddev.medialibraryapp.di

import com.syeddev.medialibraryapp.core.db.MediaGalleryDatabase
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManager
import com.syeddev.medialibraryapp.features.mediagallery.data.repository.MediaGalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryModule(firebaseStorageManager: FirebaseStorageManager,mediaGalleryDatabase: MediaGalleryDatabase): MediaGalleryRepository{
        return MediaGalleryRepository(firebaseStorageManager,mediaGalleryDatabase)
    }
}