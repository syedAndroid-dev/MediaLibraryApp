package com.syeddev.medialibraryapp.core.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.syeddev.medialibraryapp.core.manager.MediaGalleryDataSyncWorker
import com.syeddev.medialibraryapp.core.navigation.MediaLibraryNavigationGraph
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setUpDataSyncWorker()
        setContent {
            MediaLibraryAppTheme {
                MediaLibraryNavigationGraph()
            }
        }
    }


    private fun setUpDataSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<MediaGalleryDataSyncWorker>()
            .setInitialDelay(Duration.ofSeconds(5))
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(10)
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(syncRequest)

    }
}