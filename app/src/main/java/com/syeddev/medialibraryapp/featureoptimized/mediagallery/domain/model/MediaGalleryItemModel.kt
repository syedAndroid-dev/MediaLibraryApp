package com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.model

data class MediaGalleryItemModel(
    val id : Long = 0L,
    val title: String = "",
    val size: String = "",
    val downloadUrl: String = "",
    val fireStoreId: String = ""
)