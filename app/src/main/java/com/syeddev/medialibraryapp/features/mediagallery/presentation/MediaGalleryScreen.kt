package com.syeddev.medialibraryapp.features.mediagallery.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme
import com.syeddev.medialibraryapp.features.auth.presentation.signin.SignInUiEvents


@PreviewLightDark
@Composable
private fun MediaGalleryScreenContentPreview() {
    MediaLibraryAppTheme {
        MediaGalleryScreenContent()

    }
}

@Composable
fun MediaGalleryScreen(
    mediaGalleryViewModel : MediaGalleryViewModel = hiltViewModel<MediaGalleryViewModel>()
) {
    val mediaGalleryState = mediaGalleryViewModel.mediaGalleryState.collectAsStateWithLifecycle()
    val signInEvent = mediaGalleryViewModel.event.collectAsStateWithLifecycle(initialValue = SignInUiEvents.Idle)
    val scope = rememberCoroutineScope()

}


@Composable
fun MediaGalleryScreenContent() {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }


}