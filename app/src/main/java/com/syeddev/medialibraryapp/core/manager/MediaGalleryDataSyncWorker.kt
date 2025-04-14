package com.syeddev.medialibraryapp.core.manager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.repository.MediaGalleryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class MediaGalleryDataSyncWorker @AssistedInject constructor(
    private val mediaGalleryRepository: MediaGalleryRepository,
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {


    override suspend fun doWork(): Result {
        return try {
            Log.e("WorkerInside", "Worker inside calling..")
            //remoteMediator.refresh()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}