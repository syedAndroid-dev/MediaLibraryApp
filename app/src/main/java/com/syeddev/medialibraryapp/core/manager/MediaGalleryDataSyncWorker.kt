package com.syeddev.medialibraryapp.core.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syeddev.medialibraryapp.features.mediagallery.data.repository.MediaGalleryRepository
import javax.inject.Inject

class MediaGalleryDataSyncWorker(context: Context,parameters: WorkerParameters) : CoroutineWorker(context,parameters) {

    @Inject lateinit var mediaGalleryRepository: MediaGalleryRepository


    override suspend fun doWork(): Result {
        return try {
            Log.e("WorkerInside","Worker inside calling..")
            mediaGalleryRepository.syncMediaGalleryRefreshedData()
            Result.success()
        } catch (e: Exception){
            Result.retry()
        }
    }
}