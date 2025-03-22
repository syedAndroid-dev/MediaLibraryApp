package com.syeddev.medialibraryapp.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syeddev.medialibraryapp.core.manager.InternetConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val connectivityManager: InternetConnectivityManager
) : ViewModel() {

    val isInternetConnected = connectivityManager.isInternetConnected.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L), false
    )
}