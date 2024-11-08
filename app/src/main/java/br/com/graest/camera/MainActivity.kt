package br.com.graest.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.graest.camera.ui.MainScreen
import br.com.graest.camera.ui.MainViewModel
import br.com.graest.camera.ui.NavGraph
import br.com.graest.camera.ui.theme.CameraTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {
            CameraTheme {
                val scope = rememberCoroutineScope()
                val viewModel = viewModel<MainViewModel>()
                val state by viewModel.state.collectAsState()

                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                fun onSelectedItemChange(index: Int) {
                    selectedItemIndex = index
                }

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                val navController: NavHostController = rememberNavController()

                MainScreen(
                    scope = scope,
                    selectedItemIndex = selectedItemIndex,
                    onSelectedItemChange = ::onSelectedItemChange,
                    drawerState = drawerState,
                    navController = navController
                ) {
                    NavGraph(
                        navController = navController,
                        applicationContext = applicationContext,
                        viewModel = viewModel,
                        state = state
                    )
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }


}
