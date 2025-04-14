package com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.syeddev.medialibraryapp.core.db.MediaGalleryDatabase
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManager
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.mappers.toMediaGalleryEntity
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class MediaGalleryRemoteMediator(
    val mediaGalleryDatabase: MediaGalleryDatabase,
    val mediaGalleryFirebaseStorageManager: FirebaseStorageManager
): RemoteMediator<Int, MediaGalleryEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MediaGalleryEntity>
    ): MediatorResult {
        return try {
//            val isConnected = networkConnectivityManager.isInternetConnected
//
//            if(!isConnected.first()){
//                return MediatorResult.Success(endOfPaginationReached = true)
//            }

            if (loadType == LoadType.PREPEND) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = mediaGalleryFirebaseStorageManager.getAllMediaDetails().first().data?.map { it.toMediaGalleryEntity() }
            Log.e("FirebaseResponse", "Size : ${response?.size} Response: ${response}")
            mediaGalleryDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    mediaGalleryDatabase.mediaItemDao().clearAll()
                }
                Log.e("BeforeInsert","insertedData : ${mediaGalleryDatabase.mediaItemDao().getMediaItemCount()}  ${response?.size}")
                mediaGalleryDatabase.mediaItemDao().updateAllMediaItems(response ?: listOf())
                Log.e("AfterInsertCount","${mediaGalleryDatabase.mediaItemDao().getMediaItemCount()}")
            }
            Log.e("insidePaginationCalling","Inside Pagination Calling.. ${response?.isEmpty()}")
            MediatorResult.Success(endOfPaginationReached = response?.isEmpty() == true)
        } catch (e: Exception){
            MediatorResult.Error(e)
        }
    }
}