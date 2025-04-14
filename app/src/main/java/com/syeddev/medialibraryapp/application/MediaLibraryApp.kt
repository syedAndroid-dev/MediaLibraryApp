package com.syeddev.medialibraryapp.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.syeddev.medialibraryapp.core.manager.MediaGalleryDataSyncWorker
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.repository.MediaGalleryRepository
import dagger.hilt.android.HiltAndroidApp
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MediaLibraryApp() : Application(), Configuration.Provider {


    @Inject
    lateinit var workerFactory: CustomWorkerFactory
//
//    override val workManagerConfiguration: Configuration =


    override fun onCreate() {
        super.onCreate()

        WorkManager.initialize(this, workManagerConfiguration)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
}


class CustomWorkerFactory @Inject constructor(private val mediaGalleryRepository: MediaGalleryRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return MediaGalleryDataSyncWorker(mediaGalleryRepository = mediaGalleryRepository,context = appContext,parameters = workerParameters)
    }

}