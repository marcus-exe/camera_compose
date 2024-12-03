package br.com.graest.camera.data

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.graest.camera.model.Amphibian
import br.com.graest.camera.network.AppApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

interface AppRepository {
    suspend fun getAmphibians(): List<Amphibian>
    suspend fun postPhoto(imageFile: File): File
}

class DefaultAppRepository(
    private val appApiService: AppApiService
) : AppRepository {
    override suspend fun getAmphibians(): List<Amphibian> = appApiService.getAmphibians()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun postPhoto(imageFile: File): File {
        // Convert the image file into a RequestBody
        val requestBody = imageFile.asRequestBody("image/jpeg".toMediaType())

        // Send the request and handle the response
        val response = appApiService.postPhoto(requestBody)

        if (response.isSuccessful) {
            // Save or process the response file (e.g., save to local storage)
            val responseBody = response.body()
            val timestamp = LocalDateTime.now()
            val outputFile = File("image/output/", "response_${timestamp}.jpg")
            responseBody?.byteStream()?.use { inputStream ->
                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return outputFile
        } else {
            throw IOException("Failed to upload photo: ${response.code()}")
        }
    }
}
