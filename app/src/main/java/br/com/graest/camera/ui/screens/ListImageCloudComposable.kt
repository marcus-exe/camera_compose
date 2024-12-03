package br.com.graest.camera.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import br.com.graest.camera.R
import br.com.graest.camera.network.ApiStatus
import br.com.graest.camera.ui.MainEffect
import br.com.graest.camera.ui.MainEvent
import br.com.graest.camera.ui.MainUIState
import br.com.graest.camera.ui.screens.components.AsyncImageComposable
import br.com.graest.camera.ui.screens.components.ErrorScreen
import br.com.graest.camera.ui.screens.components.LoadingScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun ListImageCloudComposable(
    state: MainUIState,
    onEvent: (MainEvent) -> Unit,
    onClickDetails: () -> Unit,
    effect: Flow<MainEffect>,
    retryAction: () -> Unit,
    modifier: Modifier,
) {

    LaunchedEffect(effect) {
        effect.collect{
            when (it) {
                MainEffect.GoToCloudImageDetails -> onClickDetails()
                else -> Unit
            }
        }
    }

    when (state.apiStatus) {
        is ApiStatus.Loading -> LoadingScreen(modifier = Modifier.size(200.dp))
        is ApiStatus.Success ->

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                items(state.apiStatus.amphibians) { amphibian ->
//                    AsyncImageComposable(
//                        amphibian = amphibian,
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .clickable {
//                                onEvent(MainEvent.SetAmphibian(amphibian))
//                                onEvent(MainEvent.GoToCloudImageDetail)
//                            }
//                    )
//                }
            }
        else -> ErrorScreen(retryAction, modifier)
    }
}



