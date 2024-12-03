package br.com.graest.camera.data

import br.com.graest.camera.network.AppApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer {
    val appRepository: AppRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://container-service-1.lt9s5vlon74ra.us-east-1.cs.amazonlightsail.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: AppApiService by lazy {
        retrofit.create(AppApiService::class.java)
    }

    override val appRepository: AppRepository by lazy {
        DefaultAppRepository(retrofitService)
    }
}
