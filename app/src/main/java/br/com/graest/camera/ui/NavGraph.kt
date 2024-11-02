package br.com.graest.camera.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.graest.camera.ui.screens.CameraComposable
import br.com.graest.camera.ui.screens.ListImageLocalComposable

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    controller: LifecycleCameraController,
    applicationContext : Context,
    viewModel: MainViewModel,
    bitmaps: List<Bitmap>
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
            ListImageLocalComposable(bitmaps)
        }
        composable("Remote Images") {

        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}