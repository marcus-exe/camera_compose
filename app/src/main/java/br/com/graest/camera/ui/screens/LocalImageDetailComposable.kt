package br.com.graest.camera.ui.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.graest.camera.ui.MainEffect
import br.com.graest.camera.ui.MainEvent
import br.com.graest.camera.ui.MainUIState
import br.com.graest.camera.ui.MainViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun LocalImageDetailComposable(
    state: MainUIState,
    onEvent: (MainEvent) -> Unit,
    effect: Flow<MainEffect>,
    viewModel: MainViewModel,
) {
    LaunchedEffect(effect){
        effect.collect {
            when (it) {
                // viewModel sent stuff
                if (state.bitmaps != null && state.bitmapIndex != null)
                val bitmap = state.bitmaps[state.bitmapIndex]
                MainEffect.SendImageCloud -> viewModel.sendImageToCloud(bitmap)
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            if (state.bitmaps != null && state.bitmapIndex != null){
                Log.d("Index", "Current State Index: ${state.bitmapIndex}")
                Image(
                    bitmap = state.bitmaps[state.bitmapIndex].asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Button(onClick = { onEvent(MainEvent.SendImageCloud)}) {
            Text(text = "Send to Cloud")
        }
    }
}