package com.syeddev.medialibraryapp.core.components.commonmediaplayer

import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonMediaPlayerViewModel @Inject constructor(
    val player : Player
): ViewModel() {


    init {
        player.prepare()
        playVideo(uri = "https://www.w3schools.com/tags/mov_bbb.mp4")
    }

    fun playVideo(uri: String){
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
    }

    fun pauseVideo(){
        player.pause()
    }


    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}

data class MediaDetails(
    val title: String = "",
    val mediaUrl: String = "",
    val createdAt : String = "",
    val size : String = "",
    val mediaItem: MediaItem,
)