package br.com.graest.camera.data

import android.content.Context
import br.com.graest.camera.network.AppApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


interface AppContainer {
    val appRepository: AppRepository
}

class DefaultAppContainer(applicationContext: Context) : AppContainer {
    private val BASE_URL = "https://container-service-1.lt9s5vlon74ra.us-east-1.cs.amazonlightsail.com/"
    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: AppApiService by lazy {
        retrofit.create(AppApiService::class.java)
    }

    override val appRepository: AppRepository by lazy {
        DefaultAppRepository(applicationContext, retrofitService)
    }
}
