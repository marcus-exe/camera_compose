package br.com.graest.camera.ui.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.graest.camera.ui.MainEffect
import br.com.graest.camera.ui.MainEvent
import br.com.graest.camera.ui.MainUIState
import br.com.graest.camera.ui.MainViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import kotlinx.coroutines.flow.Flow

@Composable
fun LocalImageDetailComposable(
    state: MainUIState,
    onEvent: (MainEvent) -> Unit,
    effect: Flow<MainEffect>,
    viewModel: MainViewModel,
) {
    val context = LocalContext.current
    LaunchedEffect(effect){
        effect.collect {
            when (it) {
                // viewModel sent stuff
                MainEffect.SendImageCloud -> if (state.bitmaps != null && state.bitmapIndex != null) {
                    val bitmap = state.bitmaps[state.bitmapIndex]
                     viewModel.sendImageToCloud(context, bitmap)
                }
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
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
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier =
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(10.dp),
        ) {
            itemsIndexed(state.imagePathList) { index, imagePath ->
                val painter =
                    rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = imagePath)
                            .build(),
                    )
                Image(
                    painter = painter,
                    contentDescription =
                    "",
                    modifier =
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Button(onClick = { onEvent(MainEvent.SendImageCloud)}, enabled = !state.loading) {
            if (state.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(32.dp),
                )
            } else {
                Text(text = "Send to Cloud")
            }

        }
    }
}