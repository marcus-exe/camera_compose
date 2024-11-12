package br.com.graest.camera.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.com.graest.camera.ui.MainEffect
import br.com.graest.camera.ui.MainEvent
import br.com.graest.camera.ui.MainUIState
import br.com.graest.camera.ui.MainViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun ListImageLocalComposable(
    state: MainUIState,
    onEvent: (MainEvent) -> Unit,
    effect: Flow<MainEffect>,
    onClickGoDetails: () -> Unit
) {
    LaunchedEffect(effect){
        effect.collect {
            when (it) {
                MainEffect.GoToLocalImageDetails -> onClickGoDetails()
                else -> Unit
            }
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        state.bitmaps?.let {
            items(it.size) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Log.d("Image", "Bitmaps value: ${state.bitmaps}")
                    Image(
                        bitmap = state.bitmaps[index].asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onEvent(MainEvent.SetBitmapIndex(index))
                                onEvent(MainEvent.GoToLocalImageDetail)
                            }
                    )
                }
            }
        }
    }
}
