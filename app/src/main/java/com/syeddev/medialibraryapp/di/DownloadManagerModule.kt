package com.syeddev.medialibraryapp.di

import android.content.Context
import com.syeddev.medialibraryapp.core.manager.MediaGalleryDownloadManager
import com.syeddev.medialibraryapp.core.manager.MediaGalleryDownloadManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DownloadManagerModule {

    @Provides
    @Singleton
    fun provideDownloadManager(@ApplicationContext appContext: Context): MediaGalleryDownloadManager{
        return MediaGalleryDownloadManagerImpl(context = appContext)
    }
}