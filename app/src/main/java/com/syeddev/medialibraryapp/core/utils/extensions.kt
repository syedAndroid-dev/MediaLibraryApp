package com.syeddev.medialibraryapp.core.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel
import com.syeddev.medialibraryapp.features.mediagallery.data.model.MediaItemFireStoreModel


fun Uri.getMediaType(): MediaType {
    val extension = this.toString()
     .substringAfterLast('.', "").lowercase()
    return  when (extension) {
        in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp") -> MediaType.IMAGE
        in listOf("mp4", "mkv", "avi", "mov", "flv", "webm") ->  MediaType.VIDEO
        in listOf("mp3", "wav", "ogg", "m4a", "aac", "flac") ->  MediaType.AUDIO
        else -> MediaType.NOTHING
    }
}

fun MediaItemFireStoreModel.toMediaEntity(): MediaItemModel{
    return MediaItemModel(
        id = id,
        title = title,
        mediaType = mediaType,
        size = size,
        uploadedTime = uploadedTime,
        isMusic = isMusic,
        musicDetails = musicDetails,
        downloadUrl = downloadUrl
    )
}


fun Context.getMediaDetails(uri: Uri): Triple<String?, Long?, String?> {
    var title: String? = null
    var size: Long? = null
    var mediaType: String? = null

    val projection = arrayOf(
        MediaStore.MediaColumns.DISPLAY_NAME,
        MediaStore.MediaColumns.SIZE,
        MediaStore.MediaColumns.MIME_TYPE
    )

    contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val titleIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)
            val typeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)

            if (titleIndex != -1) title = cursor.getString(titleIndex)
            if (sizeIndex != -1) size = cursor.getLong(sizeIndex)
            if (typeIndex != -1) mediaType = cursor.getString(typeIndex)
        }
    }

    return Triple(title, size, mediaType)
}