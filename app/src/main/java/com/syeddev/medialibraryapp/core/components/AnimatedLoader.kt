package com.syeddev.medialibraryapp.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.syeddev.medialibraryapp.R

@Composable
fun AnimatedLoader(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    loaderTitle: String = "Loading.."
) {
    val preLoaderLottieAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))

    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieAnimation,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    AnimatedVisibility(visible = isLoading) {
        Dialog(
            onDismissRequest = { }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = preLoaderLottieAnimation,
                    progress = preLoaderProgress,
                    modifier = modifier.size(120.dp),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = loaderTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AnimatedPagingLoader(modifier: Modifier = Modifier) {
    val preLoaderLottieAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))

    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieAnimation,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = preLoaderLottieAnimation,
        progress = preLoaderProgress,
        modifier = modifier.size(120.dp),
        contentScale = ContentScale.Fit,
    )
}