package com.syeddev.medialibraryapp.core.components.commonaudioplayer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syeddev.medialibraryapp.R
import com.syeddev.medialibraryapp.core.components.CommonAsyncImage
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme


@Composable
fun CommonAudioPlayerScreen(
    modifier: Modifier = Modifier,
    name: String,
    artist: String,
    isPlaying: Boolean,
    musicAlbumImageUrl: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        AlbumCoverAnimation(
            isSongPlaying = isPlaying,
            musicAlbumImageUrl = musicAlbumImageUrl
        )


        Text(
            text = "Name : $name",
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Artist : $artist",
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AlbumCoverAnimation(
    modifier: Modifier = Modifier,
    isSongPlaying: Boolean = true,
    musicAlbumImageUrl: String
) {
    var currentRotation by remember {
        mutableFloatStateOf(0f)
    }

    val rotation = remember {
        Animatable(currentRotation)
    }

    LaunchedEffect(isSongPlaying) {
        if (isSongPlaying) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                rotation.animateTo(
                    targetValue = currentRotation + 50,
                    animationSpec = tween(
                        1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlbumCover(
            rotationDegrees = rotation.value,
            musicAlbumImageUrl = musicAlbumImageUrl
        )

        TrackSlider(
            value = 0f,
            onValueChange = {},
            onValueChangeFinished = {},
            songDuration = 10f
        )
        ControlButton(
            icon = if(isSongPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            size = 32.dp
        ){}
    }
}

@Composable
fun AlbumCover(
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f,
    musicAlbumImageUrl: String
) {
    val roundedShape = object : androidx.compose.ui.graphics.Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val p1 = Path().apply {
                addOval(Rect(4f, 3f, size.width - 1, size.height - 1))
            }
            val thickness = size.height / 2.10f
            val p2 = Path().apply {
                addOval(
                    Rect(
                        thickness,
                        thickness,
                        size.width - thickness,
                        size.height - thickness
                    )
                )
            }
            val p3 = Path()
            p3.op(p1, p2, PathOperation.Difference)

            return Outline.Generic(p3)
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1.0f)
            .clip(roundedShape)
    ) {
        CommonAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationDegrees),
            url = musicAlbumImageUrl
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationDegrees),
            painter = painterResource(id = R.drawable.ic_album_background),
            contentDescription = "AlBum Background"
        )

        CommonAsyncImage(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .rotate(rotationDegrees)
                .aspectRatio(1.0f)
                .align(Alignment.Center)
                .clip(roundedShape),
            url = musicAlbumImageUrl,
        )

    }
}

@Composable
fun TrackSlider(
    value: Float,
    onValueChange: (newValue: Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    songDuration: Float
) {
    Slider(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        onValueChangeFinished = {

            onValueChangeFinished()

        },
        valueRange = 0f..songDuration,
        colors = SliderDefaults.colors(
            thumbColor = Color.Black,
            activeTrackColor = Color.DarkGray,
            inactiveTrackColor = Color.Gray,
        )
    )
}

@Composable
fun ControlButton(icon: ImageVector, size: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(size / 1.5f),
            imageVector =  icon,
            tint = Color.Black,
            contentDescription = null
        )
    }
}

@PreviewLightDark
@Composable
private fun CommonAudioPlayerPreview() {
    MediaLibraryAppTheme {
        CommonAudioPlayerScreen(
            name = "syed",
            artist = "syed",
            isPlaying = false,
            musicAlbumImageUrl = ""
        )
    }
}
