package br.com.graest.camera.ui

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

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    controller: LifecycleCameraController,
) {
    NavHost(
        navController = navController,
        startDestination ="Camera"
    ) {
        composable("Camera") {
            //CameraComposable()
        }
        composable("Local Images") {

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