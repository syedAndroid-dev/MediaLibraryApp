package com.syeddev.medialibraryapp.core.components

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.syeddev.medialibraryapp.R
import com.syeddev.medialibraryapp.core.utils.MediaType
import java.io.File
import kotlin.text.startsWith

@Composable
fun CommonAsyncImage(
    modifier: Modifier = Modifier,
    url: String = "",
    alpha: Float = 1f,
    contentScale: ContentScale = ContentScale.FillHeight,
    mediaType: MediaType = MediaType.IMAGE
) {
    val context = LocalActivity.current ?: return

    val imageSource: Any = if (url.startsWith("http") == true) {
        ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build()
    } else {
        File(url)
    }

    AsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = imageSource,
        error = painterResource(
            id = when (mediaType) {
                MediaType.IMAGE -> R.drawable.ic_image_placeholder
                MediaType.VIDEO -> R.drawable.ic_video_placeholder
                MediaType.AUDIO -> R.drawable.ic_music_placeholder
            }
        ),
        placeholder = painterResource(
            id = when (mediaType) {
                MediaType.IMAGE -> R.drawable.ic_image_placeholder
                MediaType.VIDEO -> R.drawable.ic_video_placeholder
                MediaType.AUDIO -> R.drawable.ic_music_placeholder
            }
        ),
        contentDescription = "Image",
        contentScale = contentScale,
        alpha = alpha,
        onError = { error -> Log.e("CoilError", "Failed to load image: $error") }
    )
}



