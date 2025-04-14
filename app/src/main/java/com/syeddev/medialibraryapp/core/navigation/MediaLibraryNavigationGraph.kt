package com.syeddev.medialibraryapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.syeddev.medialibraryapp.core.base.BaseViewModel
import com.syeddev.medialibraryapp.featureoptimized.mediagallery.presentation.mediadetail.MediaGalleryScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun MediaLibraryNavigationGraph(
    baseViewModel: BaseViewModel = hiltViewModel<BaseViewModel>()
) {
    val internetStatus = baseViewModel.isInternetConnected.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    MediaGalleryScreen()

//    NavHost(
//        modifier = Modifier
//            .fillMaxSize(),
//        navController = navController,
//        startDestination = baseViewModel.getStartDestination(),
//        enterTransition = { fadeIn() + slideInHorizontally() },
//        exitTransition = { fadeOut() + slideOutHorizontally() },
//        popEnterTransition = { fadeIn() + slideInHorizontally() },
//        popExitTransition = { fadeOut() + slideOutHorizontally() }
//    ) {
//        composable<Destination.SignIn> {
//            SignInScreen(
//                navController = navController
//            )
//        }
//
//        composable<Destination.SignUp> {
//            SignUpScreen(
//                navController = navController
//            )
//        }
//
//        composable<Destination.MediaGallery> {
//            MediaGalleryScreen(
//                navController = navController
//            )
//        }
//
//        composable<Destination.MediaDetails> {
//            MediaGalleryDetailScreenContent(
//                navController = navController
//            )
//        }
//    }
}


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

