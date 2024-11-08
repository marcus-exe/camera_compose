package br.com.graest.camera.network

import br.com.graest.camera.model.Amphibian

sealed interface ApiStatus {
    data class Success(val amphibians: List<Amphibian>) : ApiStatus
    object Error : ApiStatus
    object Loading : ApiStatus
}