package com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mediaitems")
data class MediaGalleryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val title: String = "",
    val size: String = "",
    val downloadUrl: String = "",
    val fireStoreId: String = ""
)