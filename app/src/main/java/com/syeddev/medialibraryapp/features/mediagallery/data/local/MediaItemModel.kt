package com.syeddev.medialibraryapp.features.mediagallery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mediaItems")
data class MediaItemModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String = "",
    val mediaType: String = "",
    val size: String = "",
    val uploadedTime: Long = 0L,
    val isMusic: Boolean = false,
    val musicDetails : String = "",
    val downloadUrl: String = "",
    val fireStoreId : String = ""
)
