package br.com.graest.camera.ui

import br.com.graest.camera.model.Amphibian

sealed interface MainEvent {
    data class SetBitmapIndex(val index: Int) : MainEvent
    data object GoToLocalImageDetail : MainEvent
    data class SetAmphibian(val amphibian: Amphibian) : MainEvent
    data object GoToCloudImageDetail : MainEvent
    data object SendImageCloud: MainEvent
}