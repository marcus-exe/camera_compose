package br.com.graest.camera.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.graest.camera.ui.screens.components.DrawerContentExpanded
import br.com.graest.camera.ui.screens.components.TopAppBarComposable
import br.com.graest.camera.ui.screens.components.items
import kotlinx.coroutines.CoroutineScope

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    scope: CoroutineScope,
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
            drawerState = drawerState,
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
                    TopAppBarComposable(scope = scope, drawerState = drawerState, navController = navController)
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
