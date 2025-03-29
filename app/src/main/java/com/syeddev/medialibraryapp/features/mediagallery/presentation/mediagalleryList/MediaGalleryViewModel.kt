package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagalleryList

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.utils.MediaType
import com.syeddev.medialibraryapp.features.mediagallery.data.repository.MediaGalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaGalleryViewModel @Inject constructor(
    val mediaGalleryRepository: MediaGalleryRepository
) : ViewModel() {
    val mediaGalleryItems = mediaGalleryRepository.getMediaItems().cachedIn(viewModelScope)

    private val _event: Channel<MediaGalleryUiEvents> = Channel()
    val event = _event.receiveAsFlow()

    fun onEvent(mediaGalleryUiEvents: MediaGalleryUiEvents) {
        when (mediaGalleryUiEvents) {

            is MediaGalleryUiEvents.ButtonClick.OnUploadMedia -> {
                viewModelScope.launch {
                    onUploadUriFirestoreMedia(
                        title = mediaGalleryUiEvents.title,
                        mediaType = mediaGalleryUiEvents.mediaType.name,
                        size = mediaGalleryUiEvents.size,
                        uri = mediaGalleryUiEvents.mediaUri,
                        uploadedTime = mediaGalleryUiEvents.uploadTime
                    )
                }
            }

            is MediaGalleryUiEvents.ShowSnackBar -> {
                _event.trySend(MediaGalleryUiEvents.ShowSnackBar(message = mediaGalleryUiEvents.message))
            }

            else -> {}
        }
    }

    private suspend  fun onUploadUriFirestoreMedia(
        title: String?,
        mediaType: String,
        size: Long?,
        uri: Uri,
        uploadedTime: Long
    ) {
        val response = mediaGalleryRepository.uploadUriToFireStorage(
            title = title,
            mediaType = mediaType,
            size = size,
            uri = uri,
            uploadedTime = uploadedTime
        ).first()

        when(response){

            is Resource.Success -> {
                _event.trySend(MediaGalleryUiEvents.ShowSnackBar(message = "MediaUploaded SuccessFullly : ${response.data}"))
            }


            is Resource.Error -> {

            }
        }
    }

}

data class MediaGalleryUiState(
    val isLoading: Boolean = false,
    val userEmail: String = "",
    val userPassword: String = "",
)

sealed class MediaGalleryUiEvents {
    data object Idle : MediaGalleryUiEvents()

    sealed class ButtonClick {
        data class OnUploadMedia(
            val mediaUri: Uri = Uri.EMPTY,
            val mediaType: MediaType,
            val title: String?,
            val size: Long?,
            val uploadTime: Long
        ) : MediaGalleryUiEvents()
    }

    sealed class Navigate {
        data class MediaGalleryDetails(val id: String) : MediaGalleryUiEvents()
    }

    data class ShowSnackBar(val message: String) : MediaGalleryUiEvents()
}