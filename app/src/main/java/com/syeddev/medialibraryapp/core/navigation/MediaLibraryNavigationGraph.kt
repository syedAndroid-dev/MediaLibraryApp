package com.syeddev.medialibraryapp.core.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.syeddev.medialibraryapp.core.base.BaseViewModel
import com.syeddev.medialibraryapp.features.auth.presentation.signin.SignInScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun MediaLibraryNavigationGraph(
    baseViewModel: BaseViewModel = hiltViewModel<BaseViewModel>()
) {
    //  ProvideRootNavController {
    //  val rootNavController = localRootNavController.current

    val internetStatus = baseViewModel.isInternetConnected.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "InternetConnectionState : ${internetStatus.value}")
    }

    val navController = rememberNavController()

    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = Destination.SignIn,
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

        composable<Destination.SignIn> {
            SignInScreen()
        }

    }
}

val localRootNavController =
    compositionLocalOf<NavHostController> { error("NavController not provided") }

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

