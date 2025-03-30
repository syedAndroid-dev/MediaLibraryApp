package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagallerydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syeddev.medialibraryapp.core.navigation.Destination
import com.syeddev.medialibraryapp.features.mediagallery.data.repository.MediaGalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class MediaGalleryDetailViewModel @Inject constructor(
    mediaGalleryRepository: MediaGalleryRepository
): ViewModel() {

    val mediaGalleryItems = mediaGalleryRepository.getMediaItems().cachedIn(viewModelScope)
}