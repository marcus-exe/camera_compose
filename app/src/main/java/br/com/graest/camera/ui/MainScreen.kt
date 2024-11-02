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
import br.com.graest.camera.ui.screens.DrawerContentExpanded
import br.com.graest.camera.ui.screens.TopAppBarComposable
import br.com.graest.camera.ui.screens.items
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
                    TopAppBarComposable(scope = scope, drawerState = drawerState)
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


//    BottomSheetScaffold(
//        scaffoldState = scaffoldState,
//        sheetPeekHeight = 0.dp,
//        sheetContent = {
//            PhotoBottomSheetContent(
//                bitmaps = bitmaps,
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//        }) { padding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            CameraPreview(
//                controller = controller,
//                modifier = Modifier.fillMaxSize()
//            )
//            IconButton(
//                onClick = {
//                    controller.cameraSelector =
//                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
//                            CameraSelector.DEFAULT_FRONT_CAMERA
//                        } else CameraSelector.DEFAULT_BACK_CAMERA
//                },
//                modifier = Modifier.offset(16.dp, 16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Cameraswitch,
//                    contentDescription = "Switch Camera"
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceAround
//            ) {
//                IconButton(
//                    onClick = {
//                        scope.launch {
//                            scaffoldState.bottomSheetState.expand()
//                        }
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Photo,
//                        contentDescription = "Open Gallery"
//                    )
//                }
//
//                IconButton(
//                    onClick = {
//                        takePhoto(
//                            applicationContext = applicationContext,
//                            controller = controller,
//                            onPhotoTaken = viewModel::onTakePhoto
//                        )
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.PhotoCamera,
//                        contentDescription = "Take Photo"
//                    )
//                }
//            }
//        }
//    }
}
