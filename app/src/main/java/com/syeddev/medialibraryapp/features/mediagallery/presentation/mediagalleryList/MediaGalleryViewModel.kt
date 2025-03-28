package com.syeddev.medialibraryapp.features.mediagallery.presentation.mediagalleryList

import androidx.lifecycle.ViewModel
import com.syeddev.medialibraryapp.features.auth.presentation.signin.SignInUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MediaGalleryViewModel @Inject constructor(): ViewModel() {

    private val _mediaGalleryState = MutableStateFlow(MediaGalleryUiState())
    val mediaGalleryState = _mediaGalleryState.asStateFlow()

    private val _event: Channel<SignInUiEvents> = Channel()
    val event = _event.receiveAsFlow()

}

data class MediaGalleryUiState(
    val isLoading : Boolean = false,
    val userEmail : String = "",
    val userPassword : String = "",
)

sealed class MediaGalleryUiEvents{
    data object Idle : MediaGalleryUiEvents()

    sealed class Input{
        data class EnterUserEmail(val email: String) : MediaGalleryUiEvents()
        data class EnterUserPassword(val password: String) : MediaGalleryUiEvents()
    }

    sealed class ButtonClick{
        data object SignIn : MediaGalleryUiEvents()
    }

    sealed class Navigate{
        data object Home : MediaGalleryUiEvents()
        data object SignUp : MediaGalleryUiEvents()
    }

    data class ShowSnackBar(val message: String): MediaGalleryUiEvents()
}