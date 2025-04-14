package com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.usecases

import androidx.paging.Pager
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.data.local.MediaGalleryEntity
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.domain.repository.MediaGalleryRepository
import javax.inject.Inject

class GetAllMediaGalleryDetailsUseCase @Inject constructor(val mediaGalleryRepository: MediaGalleryRepository) {
    operator fun invoke():Pager<Int, MediaGalleryEntity> = mediaGalleryRepository.getAllMediaGalleryItems()
}