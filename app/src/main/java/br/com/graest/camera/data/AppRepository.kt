package br.com.graest.camera.data

import br.com.graest.camera.model.Amphibian
import br.com.graest.camera.network.AppApiService

interface AppRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class DefaultAppRepository(
    private val appApiService: AppApiService
) : AppRepository {
    override suspend fun getAmphibians(): List<Amphibian> = appApiService.getAmphibians()
}
