package com.syeddev.medialibraryapp.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {


    @Serializable
    data object SignIn : Destination()

    @Serializable
    data object SignUp : Destination()

    @Serializable
    data object MediaGallery: Destination()

    @Serializable
    data class MediaDetails(val id: String): Destination()
}
