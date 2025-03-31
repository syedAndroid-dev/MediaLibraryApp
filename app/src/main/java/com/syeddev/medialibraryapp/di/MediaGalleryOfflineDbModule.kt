package com.syeddev.medialibraryapp.di

import android.content.Context
import androidx.room.Room
import com.syeddev.medialibraryapp.core.db.MediaGalleryDatabase
import com.syeddev.medialibraryapp.core.utils.Constants.MEDIA_GALLERY_DB_NAME
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaGalleryOfflineDbModule {

    @Provides
    @Singleton
    fun provideMediaGalleryDataBase(@ApplicationContext context : Context): MediaGalleryDatabase{
        return Room.databaseBuilder(context, MediaGalleryDatabase::class.java, name = MEDIA_GALLERY_DB_NAME).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMediaItemDao(database: MediaGalleryDatabase): MediaItemDao{
        return database.mediaItemDao()
    }
}