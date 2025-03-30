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
import kotlinx.coroutines.flow.first

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

            val response = firebaseStorageManager.getAllMediaDetails().first().data?.map { it.toMediaEntity() }
            Log.e("FirebaseResponse", "Size : ${response?.size} Response: ${response}")

            mediaGalleryDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.e("BeforeClear","${mediaGalleryDatabase.mediaItemDao().getMediaItemCount()}")
                    mediaGalleryDatabase.mediaItemDao().clearAllMediaItems()
                    Log.e("AfterClear","${mediaGalleryDatabase.mediaItemDao().getMediaItemCount()}")
                }
                Log.e("BeforeInsert","${response?.size}")
                mediaGalleryDatabase.mediaItemDao().insertAllMediaItems(response ?: listOf())
                Log.e("AfterInsert","${mediaGalleryDatabase.mediaItemDao().getMediaItemCount()}")

                // Check RoomDB after inserting
                val count = mediaGalleryDatabase.mediaItemDao().getMediaItemCount()
                Log.e("Room Data", "Inserted into Room: $count items")
            }

            Log.e("insidePaginationCalling","Inside Pagination Calling.. ${response?.isEmpty()}")
            MediatorResult.Success(endOfPaginationReached = response?.isEmpty() == true)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}