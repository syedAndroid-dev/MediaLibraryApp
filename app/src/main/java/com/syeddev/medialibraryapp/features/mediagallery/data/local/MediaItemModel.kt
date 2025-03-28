package com.syeddev.medialibraryapp.features.mediagallery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mediaItems")
data class MediaItemModel(
    @PrimaryKey val id: Int,
    val title: String,
    val mediaType: String,
    val size: String,
    val uploadedTime: String,
    val isMusic: Boolean,
    val musicDetails : String
)
