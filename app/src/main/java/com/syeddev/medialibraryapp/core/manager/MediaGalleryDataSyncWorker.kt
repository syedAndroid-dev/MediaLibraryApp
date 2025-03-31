package com.syeddev.medialibraryapp.core.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.syeddev.medialibraryapp.features.mediagallery.data.paginator.GalleryRemoteMediator
import javax.inject.Inject

class MediaGalleryDataSyncWorker(context: Context,parameters: WorkerParameters) : CoroutineWorker(context,parameters) {

    @Inject lateinit var remoteMediator: GalleryRemoteMediator


    override suspend fun doWork(): Result {
        return try {
            Log.e("WorkerInside","Worker inside calling..")
            remoteMediator.refresh()
            Result.success()
        } catch (e: Exception){
            Result.retry()
        }
    }
}