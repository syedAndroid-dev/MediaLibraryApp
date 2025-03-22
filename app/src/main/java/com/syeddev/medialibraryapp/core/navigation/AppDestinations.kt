package com.syeddev.medialibraryapp.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {

    @Serializable
    data object MediaGallery: Destination()

    @Serializable
    data class MediaDetails(val id: String): Destination()
}
