package com.syeddev.medialibraryapp.core.manager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

interface MediaGalleryDownloadManager {
    fun downloadFile(url: String,title: String,mediaType: String): Long
}

class MediaGalleryDownloadManagerImpl(
    private val context: Context
) : MediaGalleryDownloadManager{

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String,title: String,mediaType: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(mediaType)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(title)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
        return downloadManager.enqueue(request)
    }
}

