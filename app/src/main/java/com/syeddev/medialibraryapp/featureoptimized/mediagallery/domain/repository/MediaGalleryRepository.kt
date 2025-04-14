package com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.repository

import androidx.paging.Pager
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.remote.MediaItemDto

interface MediaGalleryRepository {

    fun getAllMediaGalleryItems():Pager<Int, MediaGalleryEntity>

    suspend fun getMediaDetails(documentId: String): Resource<MediaItemDto>

    suspend fun clearAllMediaItems()

}