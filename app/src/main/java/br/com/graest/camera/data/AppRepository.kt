package br.com.graest.camera.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import br.com.graest.camera.model.Amphibian
import br.com.graest.camera.network.AppApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

interface AppRepository {
    suspend fun getAmphibians(): List<Amphibian>
    suspend fun postPhoto(context: Context, imageFile: File): File
}

class DefaultAppRepository(
    private val appApiService: AppApiService
) : AppRepository {
    override suspend fun getAmphibians(): List<Amphibian> = appApiService.getAmphibians()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun postPhoto(context: Context, imageFile: File): File {
        // Convert the image file into a RequestBody for multipart form data
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", imageFile.name, imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull()))
            .build()

        // Send the request and handle the response
        val response = appApiService.postPhoto(requestBody)

        if (response.isSuccessful) {
            // Process the response and save it to local storage
            val responseBody = response.body()

            if (responseBody != null) {
                val timestamp = LocalDateTime.now()
                // Use the app's cache directory or external files directory for better storage management
                val outputFile = File(context.cacheDir, "response_${timestamp}.jpg")

                responseBody.byteStream().use { inputStream ->
                    outputFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                return outputFile
            } else {
                throw IOException("Received empty response body")
            }
        } else {
            // Handle non-successful response with more context
            throw IOException("Failed to upload photo: ${response.code()} ${response.message()}")
        }
    }

}
