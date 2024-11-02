package br.com.graest.camera.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.graest.camera.ui.screens.DrawerContentExpanded
import br.com.graest.camera.ui.screens.TopAppBarComposable
import br.com.graest.camera.ui.screens.items
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    scaffoldState: BottomSheetScaffoldState,
    bitmaps: List<Bitmap>,
    controller: LifecycleCameraController,
    scope: CoroutineScope,
    viewModel: MainViewModel,
    applicationContext: Context,
    selectedItemIndex: Int,
    onSelectedItemChange: (Int) -> Unit,
    drawerState: DrawerState,
    navController: NavController,
    composable: @Composable () -> Unit,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ModalNavigationDrawer(
            drawerContent = {
                DrawerContentExpanded(
                    items,
                    selectedItemIndex,
                    onSelectedItemChange,
                    scope,
                    drawerState,
                    navController
                )
            }) {
            Scaffold(
                topBar = {
                    TopAppBarComposable(navController = navController, scope = scope, drawerState = drawerState)
                }
            ) { padding ->
                Column(
                    modifier = Modifier.padding(padding),
                ) {
                    composable()
                }
            }
        }
    }
}
