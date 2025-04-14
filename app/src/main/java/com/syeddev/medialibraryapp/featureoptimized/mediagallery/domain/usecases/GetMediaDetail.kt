package com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.usecases

import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.repository.MediaGalleryRepository

class GetMediaDetail(val mediaGalleryRepository: MediaGalleryRepository,val documentId: String) {
    suspend operator fun invoke() = mediaGalleryRepository.getMediaDetails(documentId)
}