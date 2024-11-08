package br.com.graest.camera.network

import br.com.graest.camera.model.Amphibian
import retrofit2.http.GET

interface AppApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}
