package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagalleryList

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.utils.formatFileSize
import com.syeddev.medialibraryapp.core.utils.valueOrDefault
import com.syeddev.medialibraryapp.features.mediagallery.data.repository.MediaGalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaGalleryViewModel @Inject constructor(
    val mediaGalleryRepository: MediaGalleryRepository
) : ViewModel() {

    private val _mediaGalleryUiState = MutableStateFlow(MediaGalleryUiState())
    val mediaGalleryUiState = _mediaGalleryUiState.asStateFlow()

    val mediaGalleryItems = mediaGalleryRepository.getMediaItems().cachedIn(viewModelScope)

    private val _event: Channel<MediaGalleryUiEvents> = Channel()
    val event = _event.receiveAsFlow()

    fun onEvent(mediaGalleryUiEvents: MediaGalleryUiEvents) {
        when (mediaGalleryUiEvents) {

            is MediaGalleryUiEvents.ButtonClick.OnUploadMedia -> {
                viewModelScope.launch {
                    onUploadUriFireStoreMedia(
                        title = mediaGalleryUiEvents.title,
                        mediaType = mediaGalleryUiEvents.mediaType,
                        size = mediaGalleryUiEvents.size,
                        uri = mediaGalleryUiEvents.mediaUri,
                        isMusic = mediaGalleryUiEvents.isMusic,
                        musicDetails = mediaGalleryUiEvents.musicDetails,
                    )
                }
            }

            else -> {}
        }
    }

    private suspend  fun onUploadUriFireStoreMedia(
        title: String?,
        mediaType: String,
        size: Long?,
        uri: Uri,
        isMusic : Boolean,
        musicDetails : String
    ) {
        _mediaGalleryUiState.update { it.copy(isLoading = true) }
        val response = mediaGalleryRepository.uploadUriToFireStorage(
            title = title,
            mediaType = mediaType,
            uri = uri,
            metaData = mapOf(
                "title" to title.valueOrDefault(),
                "mediaType" to mediaType,
                "isMusic" to (mediaType == "audio"),
                "musicDetails" to "",
                "size" to  formatFileSize(size.valueOrDefault()),
                "uploadedTime" to System.currentTimeMillis()
            )
        ).first()

        when(response){

            is Resource.Success -> {
                _mediaGalleryUiState.update { it.copy(isLoading = false) }
                _event.trySend(MediaGalleryUiEvents.ShowSnackBar(message = "MediaUploaded SuccessFully : ${response.data?.title}"))
            }


            is Resource.Error -> {
                _mediaGalleryUiState.update { it.copy(isLoading = false) }
                _event.trySend(MediaGalleryUiEvents.ShowSnackBar(message = "Failed To UploadMedia"))
            }
        }
    }

}

data class MediaGalleryUiState(
    val isLoading : Boolean = false
)

sealed class MediaGalleryUiEvents {
    data object Idle : MediaGalleryUiEvents()

    sealed class ButtonClick {
        data class OnUploadMedia(
            val mediaUri: Uri = Uri.EMPTY,
            val mediaType: String,
            val title: String?,
            val size: Long?,
            val isMusic : Boolean,
            val musicDetails: String
        ) : MediaGalleryUiEvents()
    }

    sealed class Navigate {
        data class MediaGalleryDetails(val id: String) : MediaGalleryUiEvents()
    }

    data class ShowSnackBar(val message: String) : MediaGalleryUiEvents()
}