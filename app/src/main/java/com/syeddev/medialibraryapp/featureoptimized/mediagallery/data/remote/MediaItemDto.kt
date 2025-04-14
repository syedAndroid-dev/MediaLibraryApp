package com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.remote

data class MediaItemDto(
    val id: Long = 0L,
    val title: String = "",
    val mediaType: String = "",
    val size: String = "",
    val uploadedTime: Long = 0L,
    val isMusic: Boolean = false,
    val musicDetails : String = "",
    val downloadUrl: String = "",
    val fireStoreId: String = ""
)
