package com.syeddev.medialibraryapp.featureoptimized.mediagallery.presentation.mediadetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.syeddev.medialibraryapp.core.components.AnimatedPagingLoader
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity
import kotlin.random.Random

@Composable
fun MediaGalleryScreen(
    mediaGalleryViewModel: MediaGalleryViewModel = hiltViewModel<MediaGalleryViewModel>()
) {

    val mediaGalleryPagingState = mediaGalleryViewModel.mediaGalleryPagination.collectAsLazyPagingItems()

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Media Gallery ",
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            Icons.Default.CloudUpload,
                            contentDescription = "IconAdd"
                        )
                    }
                }
//                AnimatedVisibility(
//                    visible = mediaGalleryUiState.isSyncing,
//                ) {
//                    Row (
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(color = Color.LightGray),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ){
//                        Row(
//                            modifier = Modifier.padding(10.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = "Syncing Data..",
//                                color = Color.White
//                            )
//                            Spacer(
//                                modifier = Modifier.padding(4.dp)
//                            )
//                            AnimatedPagingLoader(
//                                modifier = Modifier,
//                                size = 40.dp
//                            )
//                        }
//                    }
//                }
            }
        },
    ) { scaffoldPadding ->
        Log.e("MediaGalleryState","count : ${ mediaGalleryPagingState.itemCount}  -- ${mediaGalleryPagingState.loadState} ")
        if(mediaGalleryPagingState.loadState.refresh is LoadState.Loading){
            CircularProgressIndicator(
                modifier = Modifier,

            )
        } else {

            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp
            ) {


                items(count = mediaGalleryPagingState.itemCount) { index ->
                    if( mediaGalleryPagingState[index] != null){

                        mediaGalleryPagingState[index]?.let {  mediaItem ->
                            MediaGridItem(
                                height = Random.nextInt(from = 100, until = 300).dp,
                                mediaItem = mediaItem
                            ){ documentId ->
                                Log.e("PassingDOcumentID","Document ID : ${documentId}")
                                //  navController.navigate(route = Destination.MediaDetails(id = documentId))
                            }
                        }

                    }

                }

                item {
                    when(mediaGalleryPagingState.loadState.refresh){
                        is LoadState.Error -> {
                            Text(text = "Error loading more items")
                        }
                        LoadState.Loading -> {
                            AnimatedPagingLoader(
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        is LoadState.NotLoading -> {}
                    }
                }
            }
        }

    }
}

@Composable
fun MediaGridItem(height: Dp,mediaItem: MediaGalleryEntity,onNavigateToDetailScreen:(String)-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            onNavigateToDetailScreen(mediaItem.fireStoreId)
        }
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
//            when (mediaItem.mediaType) {
//                "image" -> {
//                    AsyncImage(
//                        model = mediaItem.downloadUrl,
//                        contentDescription = mediaItem.title,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//
//                "video" -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(MaterialTheme.colorScheme.surfaceVariant),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.PlayCircle,
//                            contentDescription = "Video",
//                            modifier = Modifier.run { size(48.dp) },
//                            tint = Color.White
//                        )
//                    }
//                }
//
//                "audio" -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(MaterialTheme.colorScheme.surfaceVariant),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Audiotrack,
//                            contentDescription = "Audio",
//                            modifier = Modifier.size(48.dp)
//                        )
//                    }
//                }
//            }

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