package br.com.graest.camera.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.graest.camera.ui.screens.CameraComposable
import br.com.graest.camera.ui.screens.CloudImageDetailComposable
import br.com.graest.camera.ui.screens.ListImageCloudComposable
import br.com.graest.camera.ui.screens.ListImageLocalComposable
import br.com.graest.camera.ui.screens.LocalImageDetailComposable

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    applicationContext : Context,
    viewModel: MainViewModel,
    state: MainUIState
    ) {
    NavHost(
        navController = navController,
        startDestination ="Camera"
    ) {
        composable("Camera") {
            CameraComposable(
                applicationContext = applicationContext,
                viewModel = viewModel
            )
        }
        composable("Local Images") {
            ListImageLocalComposable(
                state = state,
                onEvent = viewModel::onEvent,
                effect = viewModel.effect
            ) { navController.navigate("Local Image Details") }
        }

        composable("Local Image Details") {
            LocalImageDetailComposable(
                state,
                onEvent = viewModel::onEvent,
                effect = viewModel.effect,
                viewModel = viewModel
            )
        }

        composable("Remote Images") {
            ListImageCloudComposable(
                state = state,
                onEvent = viewModel::onEvent,
                onClickDetails = {
                    navController.navigate("Remote Image Details")
                },
                effect = viewModel.effect,
                retryAction = {
                    viewModel::getInfo
                },
                modifier = Modifier
            )
        }
        composable("Remote Image Details") {
            CloudImageDetailComposable(state = state, retryAction = { /*TODO*/ })
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}