package com.syeddev.medialibraryapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryDao
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity

@Database(entities = [MediaGalleryEntity::class], version = 11, exportSchema = false)
abstract class MediaGalleryDatabase: RoomDatabase() {
    abstract fun mediaItemDao(): MediaGalleryDao
}