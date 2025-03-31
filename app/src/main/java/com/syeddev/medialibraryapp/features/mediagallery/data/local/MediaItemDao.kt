package com.syeddev.medialibraryapp.features.mediagallery.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MediaItemDao {

    @Query("SELECT * FROM mediaItems ORDER BY uploadedTime DESC")
    fun getAllMediaItems(): PagingSource<Int, MediaItemModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMediaItems(mediaItems : List<MediaItemModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleMedia(mediaItem : MediaItemModel)

    @Query("DELETE FROM mediaItems")
    suspend fun clearAllMediaItems()

    @Query("SELECT COUNT(*) FROM mediaItems")
    suspend fun getMediaItemCount(): Int
}