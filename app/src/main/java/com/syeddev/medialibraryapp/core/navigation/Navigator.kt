package com.syeddev.medialibraryapp.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
    val startDestination: Destination
    val rootNavigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()

    suspend fun popUpCurrentParent()
}

class DefaultNavigator() : Navigator {

    override val startDestination: Destination = Destination.MediaGallery

    private val _rootNavigationActions = Channel<NavigationAction>()

    override val rootNavigationActions: Flow<NavigationAction> = _rootNavigationActions.receiveAsFlow()

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _rootNavigationActions.send(
            NavigationAction.Navigate(
                destination = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _rootNavigationActions.send(NavigationAction.NavigateUp)
    }

    override suspend fun popUpCurrentParent() {
        _rootNavigationActions.send(NavigationAction.ClearCurrentParent)
    }
}