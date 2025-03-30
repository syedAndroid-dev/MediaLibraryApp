package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagallerydetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.syeddev.medialibraryapp.core.components.AnimatedLoader
import com.syeddev.medialibraryapp.core.components.CommonAsyncImage
import com.syeddev.medialibraryapp.core.components.commonaudioplayer.CommonAudioPlayerScreen
import com.syeddev.medialibraryapp.core.components.commonmediaplayer.CommonMediaPlayer
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme
import com.syeddev.medialibraryapp.core.utils.MediaType

@PreviewLightDark
@Composable
private fun MediaGalleryDetailContentPreview() {
    MediaLibraryAppTheme {
        MediaGalleryDetailScreen(
            isLoading = false,
            documentTitle = "",
            documentUrl = "",
            documentType = MediaType.NOTHING,
            documentSize = "",
            documentUploadedTime = "",
            documentId = "",
            musicDetails = "",
            onEvents = {},
            onBackPressed = {}
        )
    }
}


@Composable
fun MediaGalleryDetailScreenContent(
    mediaGalleryDetailViewModel: MediaGalleryDetailViewModel = hiltViewModel<MediaGalleryDetailViewModel>(),
    navController: NavHostController
) {
    val mediaGalleryDetailUiState =
        mediaGalleryDetailViewModel.mediaGalleryDetailUiState.collectAsStateWithLifecycle()
    val mediaGalleryEvent =
        mediaGalleryDetailViewModel.event.collectAsStateWithLifecycle(MediaGalleryDetailUiEvents.Idle)


    LaunchedEffect(key1 = mediaGalleryEvent.value) {

    }
    MediaGalleryDetailScreen(
        isLoading = mediaGalleryDetailUiState.value.isLoading,
        documentTitle = mediaGalleryDetailUiState.value.documentTitle,
        documentUrl = mediaGalleryDetailUiState.value.documentUrl,
        documentType = mediaGalleryDetailUiState.value.documentType,
        documentSize = mediaGalleryDetailUiState.value.documentSize,
        documentUploadedTime = mediaGalleryDetailUiState.value.documentUploadedTime,
        documentId = mediaGalleryDetailUiState.value.documentId,
        musicDetails = mediaGalleryDetailUiState.value.musicDetails,
        onEvents = mediaGalleryDetailViewModel::onEvent,
        onBackPressed = { navController.navigateUp() }
    )
}

@Composable
fun MediaGalleryDetailScreen(
    isLoading: Boolean,
    documentTitle: String,
    documentUrl: String,
    documentType: MediaType,
    documentSize: String,
    documentUploadedTime: String,
    documentId: String,
    musicDetails: String,
    onEvents: (MediaGalleryDetailUiEvents) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onBackPressed()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "BackButton"
                    )
                }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        onEvents(
                            MediaGalleryDetailUiEvents.OnClick.OnDownLoadMedia(
                                title = documentTitle,
                                url = documentUrl,
                                mediaType = when(documentType){
                                    MediaType.IMAGE -> "image/jpeg"
                                    MediaType.VIDEO -> "video/*"
                                    MediaType.AUDIO -> "audio/*"
                                    MediaType.NOTHING -> ""
                                }
                            )
                        )
                    }
                ) {
                    Icon(
                        Icons.Default.FileDownload,
                        contentDescription = "BackButton"
                    )
                }
                IconButton(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        onEvents(MediaGalleryDetailUiEvents.OnClick.OnMediaDelete)
                    }
                ) {
                    Icon(
                        Icons.Default.DeleteOutline,
                        contentDescription = "BackButton"
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            AnimatedLoader(isLoading = isLoading)

            Column(
                modifier = Modifier
                    .weight(.5f)
                    .padding(10.dp)
            ) {
                when (documentType) {
                    MediaType.IMAGE -> {
                        CommonAsyncImage(
                            url = documentUrl
                        )
                    }

                    MediaType.VIDEO -> {
                        CommonMediaPlayer(
                            modifier = Modifier.weight(1f),
                            mediaUrl = documentUrl
                        )
                    }

                    MediaType.AUDIO -> {
                        CommonAudioPlayerScreen(
                            name = "syed",
                            artist = "syed",
                            isPlaying = false,
                            musicAlbumImageUrl = ""
                        )

                    }

                    MediaType.NOTHING -> {}
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.5f)
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Document Title : ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = documentTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Document Size : ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = documentSize,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Document Upload Details : ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = documentUploadedTime,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


            }

        }
    }
}