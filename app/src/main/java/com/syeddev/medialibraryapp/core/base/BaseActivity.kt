package com.syeddev.medialibraryapp.core.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.syeddev.medialibraryapp.core.navigation.MediaLibraryNavigationGraph
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaLibraryAppTheme {
                MediaLibraryNavigationGraph()
            }
        }
    }
}