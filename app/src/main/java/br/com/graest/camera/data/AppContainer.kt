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
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
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
