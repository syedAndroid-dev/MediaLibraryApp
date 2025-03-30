package com.syeddev.medialibraryapp.core.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.provider.MediaStore
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel
import com.syeddev.medialibraryapp.features.mediagallery.data.model.MediaItemFireStoreModel
import java.util.Locale


fun MediaItemFireStoreModel.toMediaEntity(): MediaItemModel {
    return MediaItemModel(
        id = id,
        title = title,
        mediaType = mediaType,
        size = size,
        uploadedTime = uploadedTime,
        isMusic = isMusic,
        musicDetails = musicDetails,
        downloadUrl = downloadUrl,
        fireStoreId = fireStoreId
    )
}

fun formatFileSize(sizeInBytes: Long): String {
    val units = listOf("B", "KB", "MB", "GB")
    var size = sizeInBytes.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.lastIndex) {
        size /= 1024
        unitIndex++
    }

    return "%.1f %s".format(size, units[unitIndex])
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
            if (typeIndex != -1) mediaType = when {
                cursor.getString(typeIndex).startsWith("image/") -> "image"
                cursor.getString(typeIndex).startsWith("video/") -> "video"
                cursor.getString(typeIndex).startsWith("audio/") -> "audio"
                else -> "unknown"
            }
        }
    }

    return Triple(title, size, mediaType)
}


fun String?.valueOrDefault(default: String = ""): String = if (this !== null) this else default

fun String?.isValidEmail() = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$").matches(this.valueOrDefault())

fun String.isValidPassword() = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}").matches(this.valueOrDefault())

fun Int?.valueOrDefault(default: Int = 0): Int = this ?: default

fun Float?.valueOrDefault(default: Float = 0.0f): Float = this ?: default

fun Double?.valueOrDefault(default: Double = 0.0): Double = this ?: default

fun Long?.valueOrDefault(default: Long = 0): Long = this ?: default

fun Long?.formatDate(): String{
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}