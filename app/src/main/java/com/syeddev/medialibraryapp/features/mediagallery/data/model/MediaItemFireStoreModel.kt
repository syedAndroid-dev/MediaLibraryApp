package com.syeddev.medialibraryapp.features.mediagallery.data.model

data class MediaItemFireStoreModel(
    val id: Long = 0L,
    val title: String = "",
    val mediaType: String = "",
    val size: String = "",
    val uploadedTime: String = "",
    val isMusic: Boolean = false,
    val musicDetails : String = "",
    val downloadUrl: String = ""
)
