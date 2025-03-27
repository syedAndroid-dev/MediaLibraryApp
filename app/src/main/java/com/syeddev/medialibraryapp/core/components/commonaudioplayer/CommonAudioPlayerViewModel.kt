package com.syeddev.medialibraryapp.core.components.commonaudioplayer

import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonAudioPlayerViewModel @Inject constructor(
    val player : Player
): ViewModel() {


}

data class MusicDetails(
    val name: String = "",
    val artist: String = "",
    val musicUrl: String = "",
    val cover: String = "",

    val totalDuration: Long = 0L,
    val currentDuration: Long = 0L,
)