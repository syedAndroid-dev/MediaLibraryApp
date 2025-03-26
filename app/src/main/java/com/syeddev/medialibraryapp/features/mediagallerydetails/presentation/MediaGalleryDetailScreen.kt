package com.syeddev.medialibraryapp.features.mediagallerydetails.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme

@PreviewLightDark
@Composable
private fun MediaGalleryDetailContentPreview() {
    MediaLibraryAppTheme {
        MediaGalleryDetailScreenContent()

    }
}


@Composable
fun MediaGalleryDetailScreenContent(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Media Gallery Screen Detail")
    }
}