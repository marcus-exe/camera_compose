package br.com.graest.camera.network

import br.com.graest.camera.model.Amphibian

sealed interface ApiStatus {
    object Success : ApiStatus
    object Error : ApiStatus
    object Loading : ApiStatus
}