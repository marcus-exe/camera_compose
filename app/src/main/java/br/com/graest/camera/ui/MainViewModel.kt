package br.com.graest.camera.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.graest.camera.BaseApplication
import br.com.graest.camera.data.AppRepository
import br.com.graest.camera.network.ApiStatus
import br.com.graest.retinografo.base.arch.CoreViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

class MainViewModel(
    private val appRepository: AppRepository
): CoreViewModel<MainUIState, MainEffect>(MainUIState()) {

    init {
        getInfo()
    }

    fun onEvent(event: MainEvent) : Unit {
        when (event) {
            is MainEvent.SetBitmapIndex -> setState { it.copy(bitmapIndex = event.index) }
            is MainEvent.GoToLocalImageDetail -> sendEffect(MainEffect.GoToLocalImageDetails)
            is MainEvent.SetPathIndex -> setState { it.copy(imagePathIndex = event.index) }
            is MainEvent.GoToCloudImageDetail -> sendEffect(MainEffect.GoToCloudImageDetails)
            is MainEvent.SendImageCloud -> sendEffect(MainEffect.SendImageCloud)
        }
    }

    fun onTakePhoto(bitmap: Bitmap) {
        setState { it.copy(
            bitmaps = it.bitmaps?.plus(bitmap)
        ) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendImageToCloud(context: Context, bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                val tempFile = File.createTempFile(
                    LocalDateTime.now().toString().replace(":", "-"),
                    ".tmp",
                    context.cacheDir
                )
                val imageFile = bitmapToFile(bitmap, tempFile)
                val receivedFile = appRepository.postPhoto(context, imageFile)
                val filePath = getFilePath(receivedFile)
                setState {
                    it.copy(
                        apiStatus = ApiStatus.Success,
                        imagePathList = it.imagePathList + filePath
                    )
                }
            } catch (e: IOException) {
                setState {
                    it.copy(apiStatus = ApiStatus.Error)
                }
            } catch (e: HttpException) {
                setState {
                    it.copy(apiStatus = ApiStatus.Error)
                }
            }
        }
    }

    fun getInfo() {
        viewModelScope.launch {
            try {
                val amphibians = appRepository.getAmphibians()
                setState {
                    it.copy(apiStatus = ApiStatus.Success)
                }
            } catch (e: IOException) {
                setState {
                    it.copy(apiStatus = ApiStatus.Error)
                }
            } catch (e: HttpException) {
                setState {
                    it.copy(apiStatus = ApiStatus.Error)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BaseApplication)
                val appRepository = application.container.appRepository
                MainViewModel(appRepository = appRepository)
            }
        }
    }

    fun getFilePath(file: File): String {
        return file.absolutePath
    }

    fun bitmapToFile(
        bitmap: Bitmap,
        file: File,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        quality: Int = 100
    ): File {
        try {
            FileOutputStream(file).use { outputStream ->
                if (!bitmap.compress(format, quality, outputStream)) {
                    throw IOException("Failed to compress the Bitmap.")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
        return file
    }
}