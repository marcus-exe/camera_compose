package br.com.graest.camera.ui

import br.com.graest.camera.model.Amphibian

sealed interface MainEvent {
    data class SetBitmapIndex(val index: Int) : MainEvent
    data class SetAmphibian(val amphibian: Amphibian) : MainEvent
}