package com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.mappers

import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.remote.MediaItemDto
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.model.MediaGalleryItemModel

fun MediaItemDto.toMediaGalleryEntity(): MediaGalleryEntity {
    return MediaGalleryEntity(
        title = title,
        size = size,
        downloadUrl = downloadUrl,
        fireStoreId = fireStoreId
    )
}

fun MediaGalleryEntity.toMediaGalleryItem(): MediaGalleryItemModel {
    return MediaGalleryItemModel(
        id = id,
        title = title,
        size = size,
        downloadUrl = downloadUrl,
        fireStoreId = fireStoreId
    )
}