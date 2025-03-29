package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagalleryList

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme
import com.syeddev.medialibraryapp.core.utils.getMediaDetails
import com.syeddev.medialibraryapp.core.utils.getMediaType
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel


@PreviewLightDark
@Composable
private fun MediaGalleryScreenContentPreview() {
    MediaLibraryAppTheme {
        MediaGalleryScreenContent()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaGalleryScreen(
    mediaGalleryViewModel: MediaGalleryViewModel = hiltViewModel<MediaGalleryViewModel>()
) {
    val mediaGalleryItems = mediaGalleryViewModel.mediaGalleryItems.collectAsLazyPagingItems()
    val mediaGalleryEvents = mediaGalleryViewModel.event.collectAsStateWithLifecycle(initialValue = MediaGalleryUiEvents.Idle)

    var isMediaUploadBottomSheetVisible by remember { mutableStateOf(false) }
    val bottomSheetState  = rememberModalBottomSheetState()
    val snackBarState = SnackbarHostState()

    val context = LocalActivity.current?:return

    val mediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        Log.e("SelectedUri","Uri : $uri")
        if (uri != null) {
            val uriDetails = context.getMediaDetails(uri = uri)
            mediaGalleryViewModel.onEvent(
                mediaGalleryUiEvents = MediaGalleryUiEvents.ButtonClick.OnUploadMedia(
                    mediaUri = uri,
                    mediaType = uri.getMediaType(),
                    title = uriDetails.first,
                    size = uriDetails.second,
                    uploadTime = System.currentTimeMillis()
                )
            )
        } else {
            mediaGalleryViewModel.onEvent(
                mediaGalleryUiEvents = MediaGalleryUiEvents.ShowSnackBar(message = "Please Select Media To Upload.")
            )
        }
    }

    LaunchedEffect(key1 = mediaGalleryEvents.value) {
        when(mediaGalleryEvents.value){
            is MediaGalleryUiEvents.ShowSnackBar -> {
                snackBarState.showSnackbar(message = (mediaGalleryEvents.value as MediaGalleryUiEvents.ShowSnackBar).message)
            }

            else -> {}
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        isMediaUploadBottomSheetVisible = true
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "IconAdd"
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
    ) { scaffoldPadding ->
        Log.e("MediaGalleryItems","Media : ${mediaGalleryItems.itemCount}")
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = mediaGalleryItems.itemCount,
                key = { index -> mediaGalleryItems[index]?.id ?: index }
            ) { index ->
                mediaGalleryItems[index]?.let { mediaItem ->
                    MediaGridItem(mediaItem = mediaItem)
                }
            }

            item {
                Log.e("LoadState","Load State : ${mediaGalleryItems.loadState.append}")
                when(mediaGalleryItems.loadState.append){
                    is LoadState.Error -> {
                        Text(text = "Error loading more items")
                    }
                    LoadState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is LoadState.NotLoading -> {
                        Text(text = "Not loading more items")
                    }
                }
            }
        }
    }

    if (isMediaUploadBottomSheetVisible){
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                isMediaUploadBottomSheetVisible = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "Select Media Type",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            mediaLauncher.launch("image/*")
                            isMediaUploadBottomSheetVisible = false
                        }
                    ) {
                        Text(
                            text = "Image",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            mediaLauncher.launch("video/*")
                            isMediaUploadBottomSheetVisible = false
                        }
                    ) {
                        Text(
                            text = "Video",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            mediaLauncher.launch("audio/*")
                            isMediaUploadBottomSheetVisible = false
                        }
                    ) {
                        Text(
                            text = "Audio",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun MediaGridItem(mediaItem: MediaItemModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            when (mediaItem.mediaType) {
                "image" -> {
                    AsyncImage(
                        model = mediaItem.downloadUrl,
                        contentDescription = mediaItem.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                "video" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Video",
                            modifier = Modifier.run { size(48.dp) },
                            tint = Color.White
                        )
                    }
                }
                "audio" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Audiotrack,
                            contentDescription = "Audio",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            Text(
                text = mediaItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MediaGalleryScreenContent() {


}