package com.syeddev.medialibraryapp.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syeddev.medialibraryapp.core.manager.FirebaseAuthManager
import com.syeddev.medialibraryapp.core.manager.InternetConnectivityManager
import com.syeddev.medialibraryapp.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    connectivityManager: InternetConnectivityManager,
    val firebaseAuthManager: FirebaseAuthManager
) : ViewModel() {
    val isInternetConnected = connectivityManager.isInternetConnected.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)


    fun getStartDestination(): Destination{
        return if (firebaseAuthManager.getCurrentUser() != null) Destination.MediaGallery else Destination.SignIn
    }

}