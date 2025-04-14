package com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MediaGalleryDao {

    @Upsert
    suspend fun updateAllMediaItems(mediaItems : List<MediaGalleryEntity>)

    @Query("SELECT * FROM mediaitems")
    fun mediaGalleryPagingSource(): PagingSource<Int, MediaGalleryEntity>

    @Query("DELETE FROM mediaitems")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM mediaItems")
    suspend fun getMediaItemCount(): Int

}