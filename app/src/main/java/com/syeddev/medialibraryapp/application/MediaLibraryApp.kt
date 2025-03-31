package com.syeddev.medialibraryapp.application

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.syeddev.medialibraryapp.core.manager.MediaGalleryDataSyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MediaLibraryApp: Application(){
    override fun onCreate() {
        super.onCreate()
        setUpDataSyncWorker()
    }

    private fun setUpDataSyncWorker(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<MediaGalleryDataSyncWorker>( 6, TimeUnit.HOURS,
            1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("MediaGallerySync", ExistingPeriodicWorkPolicy.KEEP,syncRequest)

    }
}