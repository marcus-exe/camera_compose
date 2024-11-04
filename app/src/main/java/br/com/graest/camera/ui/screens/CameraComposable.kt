package br.com.graest.camera.ui.screens

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.graest.camera.R
import br.com.graest.camera.ui.MainViewModel
import br.com.graest.camera.ui.screens.components.CameraPreview
import br.com.graest.camera.utils.CameraUtils.takePhoto

@Composable
fun CameraComposable(
    applicationContext: Context,
    viewModel: MainViewModel
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController =
        remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE,
                )
                setZoomRatio(1.0F)
                bindToLifecycle(lifecycleOwner)
            }
        }

    LaunchedEffect(Unit) {
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {

        MainCameraComposable(controller = cameraController)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){  
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    takePhoto(
                        applicationContext = applicationContext,
                        controller = cameraController,
                        onPhotoTaken = viewModel::onTakePhoto
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take Photo",
                    tint = Color.White
                )
            }
        }

    }
}


@Composable
private fun MainCameraComposable(controller: LifecycleCameraController) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        CameraPreview(
            controller = controller,
            modifier =
            Modifier
                .fillMaxSize()
                .clip(shape = RectangleShape)
                .aspectRatio(1f)
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
        )
        Image(
            painter = painterResource(id = R.drawable.cameraoverlay),
            contentDescription = null,
        )
    }
}

