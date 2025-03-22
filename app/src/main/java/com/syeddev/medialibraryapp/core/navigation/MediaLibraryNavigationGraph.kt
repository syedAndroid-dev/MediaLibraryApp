package com.syeddev.medialibraryapp.core.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun MediaLibraryNavigationGraph() {
  //  ProvideRootNavController {
        val rootNavController = localRootNavController.current

        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = rootNavController,
            startDestination = Destination.MediaGallery,
            enterTransition = {
                fadeIn() + slideInHorizontally()
            },
            exitTransition = {
                fadeOut() + slideOutHorizontally()
            },
            popEnterTransition = {
                fadeIn() + slideInHorizontally()
            },
            popExitTransition = {
                fadeOut() + slideOutHorizontally()
            }
        ) {

        }
  //  }
}

val localRootNavController = compositionLocalOf<NavHostController> { error("NavController not provided") }

//@Composable
//fun ProvideRootNavController(
//    rootNavController: NavHostController = rememberNavController(),
//    content: @Composable () -> Unit,
//) {
//    CompositionLocalProvider(localRootNavController provides rootNavController) {
//        val navController = localRootNavController.current
//        ObserveAsEvents(
//            flow = AlShaqab.appModule.navigator.rootNavigationActions
//        ) { action ->
//            when (action) {
//                is NavigationAction.Navigate -> navController.navigate(
//                    route = action.destination,
//                    builder = action.navOptions
//                )
//
//                NavigationAction.NavigateUp -> navController.navigateUp()
//
//                NavigationAction.ClearCurrentParent -> navController.popBackStack(
//                    route = Destination.Auth.SignIn,
//                    inclusive = true
//                )
//
//                else -> {}
//            }
//        }
//        content()
//    }
//}

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}

