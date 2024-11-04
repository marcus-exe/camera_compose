package br.com.graest.camera.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.graest.camera.ui.screens.CameraComposable
import br.com.graest.camera.ui.screens.ListImageCloudComposable
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
            ListImageLocalComposable(
                bitmaps,
                viewModel
            ) { navController.navigate("Local Image Details") }
        }
        composable("Remote Images") {
            val imageUrls :List<String> = listOf(
                "https://i.pinimg.com/originals/2d/f6/db/2df6dbe8ff3e019bc25c43617ba5150d.jpg",
                "https://wallpapercave.com/wp/wp7313876.jpg",
                "https://www.wideopenspaces.com/wp-content/uploads/sites/6/2022/06/orange-cat-breeds-1.png",
                "https://cdn.powerofpositivity.com/wp-content/uploads/2020/11/Science-Explains-Why-Orange-Cats-Are-The-Most-Special-1600x900.jpg"
            )
            ListImageCloudComposable(imageUrls = imageUrls)
        }
        composable("Local Image Details") {

        }
        composable("Remote Image Details") {

        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}