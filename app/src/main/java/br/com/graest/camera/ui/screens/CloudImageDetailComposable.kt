package br.com.graest.camera.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import br.com.graest.camera.R
import br.com.graest.camera.network.ApiStatus
import br.com.graest.camera.ui.MainEffect
import br.com.graest.camera.ui.MainUIState
import br.com.graest.camera.ui.screens.components.AsyncImageComposable
import br.com.graest.camera.ui.screens.components.ErrorScreen
import br.com.graest.camera.ui.screens.components.LoadingScreen

@Composable
fun CloudImageDetailComposable(
    state: MainUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.apiStatus) {
        is ApiStatus.Loading -> LoadingScreen(modifier.size(200.dp))
        is ApiStatus.Success ->
            if (state.amphibian != null) {
                AsyncImageComposable(
                    amphibian = state.amphibian,
                    modifier = modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                )
            }
        else -> ErrorScreen(retryAction, modifier)
    }
}


