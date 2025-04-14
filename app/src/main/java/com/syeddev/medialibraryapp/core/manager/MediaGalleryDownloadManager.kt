package com.syeddev.medialibraryapp.core.manager

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.core.net.toUri

interface MediaGalleryDownloadManager {
    fun downloadFile(url: String,title: String,mediaType: String): Long
}

class MediaGalleryDownloadManagerImpl(
    context: Context
) : MediaGalleryDownloadManager{

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String,title: String,mediaType: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .apply {
                setMimeType(mediaType)
                setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI or
                            DownloadManager.Request.NETWORK_MOBILE
                )
                setNotificationVisibility(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                    } else {
                        DownloadManager.Request.VISIBILITY_VISIBLE
                    }
                )
                setTitle(title)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        title
                    )
                } else {
                    setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        createValidFileName(title, mediaType)
                    )
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    setRequiresCharging(false)
                    setRequiresDeviceIdle(false)
                }
            }

        return downloadManager.enqueue(request)
    }

    private fun createValidFileName(title: String, mediaType: String): String {
        val extension = when {
            mediaType.contains("image") -> ".jpg"
            mediaType.contains("video") -> ".mp4"
            mediaType.contains("audio") -> ".mp3"
            else -> ".bin"
        }

        val cleanTitle = title.replace("[^a-zA-Z0-9-_. ]".toRegex(), "_")
        return "$cleanTitle$extension"
    }
}

