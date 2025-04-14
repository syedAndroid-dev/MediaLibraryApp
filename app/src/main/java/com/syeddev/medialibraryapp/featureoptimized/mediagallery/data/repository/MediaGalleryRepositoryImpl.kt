package com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.db.MediaGalleryDatabase
import com.syeddev.medialibraryapp.core.manager.FirebaseStorageManager
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.remote.MediaGalleryRemoteMediator
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.remote.MediaItemDto
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.model.MediaGalleryItemModel
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.repository.MediaGalleryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MediaGalleryRepositoryImpl @Inject constructor(
    val mediaGalleryDatabase: MediaGalleryDatabase,
    val mediaGalleryFirebaseStorageManager: FirebaseStorageManager,
):MediaGalleryRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllMediaGalleryItems(): Pager<Int, MediaGalleryEntity> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            remoteMediator = MediaGalleryRemoteMediator(
                mediaGalleryDatabase = mediaGalleryDatabase,
                mediaGalleryFirebaseStorageManager = mediaGalleryFirebaseStorageManager
            ),
            pagingSourceFactory = {
                mediaGalleryDatabase.mediaItemDao().mediaGalleryPagingSource()
            }
        )
    }

    override suspend fun getMediaDetails(documentId: String): Resource<MediaItemDto> {
        return mediaGalleryFirebaseStorageManager.getMediaDetail(documentId).first()
    }

    override suspend fun clearAllMediaItems() {
        mediaGalleryDatabase.mediaItemDao().clearAll()
    }

}