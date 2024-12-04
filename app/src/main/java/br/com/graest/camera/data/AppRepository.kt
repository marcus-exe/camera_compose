package br.com.graest.camera.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import br.com.graest.camera.model.Amphibian
import br.com.graest.camera.network.AppApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

interface AppRepository {
    suspend fun getAmphibians(): List<Amphibian>
    suspend fun postPhoto(imageFile: File): File
}

class DefaultAppRepository(
    private val context: Context,
    private val appApiService: AppApiService
) : AppRepository {
    override suspend fun getAmphibians(): List<Amphibian> = appApiService.getAmphibians()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun postPhoto(imageFile: File): File {
        // Convert the image file into a RequestBody
        val requestBody = imageFile.asRequestBody("image/*".toMediaType())
        // Send the request and handle the response
        val multipartBody = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)
        val response = appApiService.postPhoto(multipartBody)

        if (response.isSuccessful) {
            // Save or process the response file (e.g., save to local storage)
            val responseBody = response.body()
            val timestamp = LocalDateTime.now()
            val directory = File(context.cacheDir,"image/output/")
            if (!directory.exists()) directory.mkdirs()
            val file = withContext(Dispatchers.IO) {
                File.createTempFile("response_", ".jpg", directory)
            }
            responseBody?.byteStream()?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return file
        } else {
            throw IOException("Failed to upload photo: ${response.code()}")
        }

    }
}
