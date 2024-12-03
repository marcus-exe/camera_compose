package br.com.graest.camera.network

import br.com.graest.camera.model.Amphibian
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AppApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>

    @Headers("Content-Type: image/jpeg", "Accept: image/jpeg")
    @POST("image")
    suspend fun postPhoto(@Body image: RequestBody): Response<ResponseBody>
}
