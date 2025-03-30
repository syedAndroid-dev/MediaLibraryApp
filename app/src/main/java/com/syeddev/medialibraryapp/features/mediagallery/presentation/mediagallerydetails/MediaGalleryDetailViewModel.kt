package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagallerydetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.manager.MediaGalleryDownloadManager
import com.syeddev.medialibraryapp.core.utils.MediaType
import com.syeddev.medialibraryapp.core.utils.formatDate
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
class MediaGalleryDetailViewModel @Inject constructor(
    val mediaGalleryRepository: MediaGalleryRepository,
    val savedStateHandle: SavedStateHandle,
    val mediaGalleryDownloadManager: MediaGalleryDownloadManager
) : ViewModel() {
    private val _mediaGalleryDetailUiState = MutableStateFlow(MediaGalleryDetailUiState())
    val mediaGalleryDetailUiState = _mediaGalleryDetailUiState.asStateFlow()

    private val _event: Channel<MediaGalleryDetailUiEvents> = Channel()
    val event = _event.receiveAsFlow()


    init {
        getMediaDetail()
    }

    fun onEvent(mediaGalleryDetailUiEvents: MediaGalleryDetailUiEvents){

        when(mediaGalleryDetailUiEvents){
            is MediaGalleryDetailUiEvents.OnClick.OnDownLoadMedia -> {
                mediaGalleryDownloadManager.downloadFile(
                    url = mediaGalleryDetailUiEvents.url,
                    title = mediaGalleryDetailUiEvents.title,
                    mediaType = mediaGalleryDetailUiEvents.mediaType
                )
            }
            MediaGalleryDetailUiEvents.OnClick.OnMediaDelete -> TODO()
            else -> {}
        }
    }

    private fun getMediaDetail() {
        viewModelScope.launch {
            _mediaGalleryDetailUiState.update { it.copy(isLoading = true) }

            val documentId = savedStateHandle["id"] ?: ""
            Log.e("DocumentID","Document ID : ${documentId}")

            val response = mediaGalleryRepository.getMediaItem(documentId = documentId).first()

            when (response) {
                is Resource.Success -> {
                    val data = response.data
                    _mediaGalleryDetailUiState.update {
                        it.copy(
                            isLoading = false,
                            documentTitle = data?.title.valueOrDefault(),
                            documentUrl = data?.downloadUrl.valueOrDefault(),
                            documentType = when(data?.mediaType.valueOrDefault("image")){
                                "image" -> MediaType.IMAGE
                                "video" -> MediaType.VIDEO
                                "audio" -> MediaType.AUDIO
                                else -> MediaType.NOTHING
                            },
                            documentSize = data?.size.valueOrDefault(),
                            documentUploadedTime = data?.uploadedTime.formatDate(),
                            documentId = documentId,
                            musicDetails = data?.musicDetails.valueOrDefault()
                        )
                    }
                }

                is Resource.Error -> {
                    _mediaGalleryDetailUiState.update { it.copy(isLoading = false) }
                    _event.trySend(MediaGalleryDetailUiEvents.ShowSnackBar(message = "Unable To Get The Media Details."))
                }
            }
        }
    }

}

data class MediaGalleryDetailUiState(
    val isLoading: Boolean = false,
    val documentTitle: String = "",
    val documentUrl: String = "",
    val documentType: MediaType = MediaType.NOTHING,
    val documentSize: String = "",
    val documentUploadedTime: String = "",
    val documentId: String = "",
    val musicDetails: String = ""
)

sealed class MediaGalleryDetailUiEvents {
    data object Idle : MediaGalleryDetailUiEvents()

    sealed class OnClick {
        data class OnDownLoadMedia(
            val title: String = "",
            val url: String = "",
            val mediaType: String = "",
        ) : MediaGalleryDetailUiEvents()

        data object OnMediaDelete : MediaGalleryDetailUiEvents()
    }

    data class ShowSnackBar(val message: String) : MediaGalleryDetailUiEvents()
}