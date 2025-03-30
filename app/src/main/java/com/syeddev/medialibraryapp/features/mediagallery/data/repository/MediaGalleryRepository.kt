package com.syeddev.medialibraryapp.features.mediagallery.data.repository

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.db.MediaGalleryDatabase
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManager
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel
import com.syeddev.medialibraryapp.features.mediagallery.data.paginator.GalleryRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.String

class MediaGalleryRepository @Inject constructor(
    val firebaseStorageManager: FirebaseStorageManager,
    val mediaGalleryDatabase: MediaGalleryDatabase,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getMediaItems(): Flow<PagingData<MediaItemModel>> {

        val pagingSourceFactory = { mediaGalleryDatabase.mediaItemDao().getAllMediaItems() }

        return Pager(
            config = PagingConfig(pageSize = 2, enablePlaceholders = false),
            remoteMediator = GalleryRemoteMediator(firebaseStorageManager, mediaGalleryDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun uploadUriToFireStorage(
        title: String?,
        mediaType: String,
        uri: Uri,
        metaData: Map<String, Any>
    ): Flow<Resource<MediaItemModel>> = callbackFlow {
        val fireStoreUploadedMedia = firebaseStorageManager.uploadMedia(
            fileName = title ?: "",
            mediaUri = uri,
            mediaType = mediaType,
            metadata = metaData
        ).first().data

        mediaGalleryDatabase.withTransaction {
            mediaGalleryDatabase.mediaItemDao().insertSingleMedia(fireStoreUploadedMedia ?: MediaItemModel())
            trySend(Resource.Success(data = fireStoreUploadedMedia))
        }
    }
}