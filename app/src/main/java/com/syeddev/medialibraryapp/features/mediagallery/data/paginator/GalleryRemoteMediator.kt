package com.syeddev.medialibraryapp.features.mediagallery.data.paginator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.syeddev.medialibraryapp.core.db.MediaGalleryDatabase
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManager
import com.syeddev.medialibraryapp.core.utils.toMediaEntity
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel

@OptIn(ExperimentalPagingApi::class)
class GalleryRemoteMediator(
    private val firebaseStorageManager : FirebaseStorageManager,
    private val mediaGalleryDatabase: MediaGalleryDatabase,
): RemoteMediator<Int, MediaItemModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MediaItemModel>
    ): MediatorResult {
        return try {
            if (loadType == LoadType.PREPEND) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            if (loadType == LoadType.REFRESH) {
                mediaGalleryDatabase.mediaItemDao().clearAllMediaItems()
            }

            val response = firebaseStorageManager.getAllMediaDetails().data?.map { it.toMediaEntity() }
            Log.e("FirebaseResponse", "Response: ${response}")

            mediaGalleryDatabase.withTransaction {
                mediaGalleryDatabase.mediaItemDao().insertAllMediaItems(response ?: listOf())
            }

            Log.e("insidePaginationCalling","Inside Pagination Calling..")
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}