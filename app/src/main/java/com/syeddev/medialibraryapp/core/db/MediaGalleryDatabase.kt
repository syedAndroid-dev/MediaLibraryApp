package com.syeddev.medialibraryapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemDao
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel

@Database(entities = [MediaItemModel::class], version = 5, exportSchema = false)
abstract class MediaGalleryDatabase: RoomDatabase() {
    abstract fun mediaItemDao(): MediaItemDao
}