package com.syeddev.medialibraryapp.featureoptimized.mediagallery.presentation.mediadetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.usecases.GetAllMediaGalleryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaGalleryViewModel @Inject constructor(
    getAllMediaGalleryDetails : GetAllMediaGalleryDetailsUseCase
): ViewModel() {

    val mediaGalleryPagination = getAllMediaGalleryDetails().flow.cachedIn(viewModelScope)

}