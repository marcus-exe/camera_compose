package br.com.graest.camera.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.graest.camera.BaseApplication
import br.com.graest.camera.data.AppRepository
import br.com.graest.camera.network.ApiStatus
import br.com.graest.camera.utils.CameraUtils.saveBitmapToExternalStorage
import br.com.graest.retinografo.base.arch.CoreViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

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
            is MainEvent.SetAmphibian -> setState { it.copy(amphibian = event.amphibian) }
            is MainEvent.GoToCloudImageDetail -> sendEffect(MainEffect.GoToCloudImageDetails)
            is MainEvent.SendImageCloud -> sendEffect(MainEffect.SendImageCloud)
        }
    }

    fun onTakePhoto(bitmap: Bitmap) {
        setState { it.copy(
            bitmaps = it.bitmaps?.plus(bitmap)
        ) }
    }

    fun sendImageToCloud(context: Context,bitmap: Bitmap) {
        setState { it.copy(loading = true) }
        viewModelScope.launch {
            try {
                saveBitmapToExternalStorage(
                    context,
                    bitmap,
                    System.currentTimeMillis().toString(),
                    {}
                )?.also { imageFile ->
                    handleFile(imageFile)
                }

            } catch (e: IOException) {
                setState {
                    it.copy(apiStatus = ApiStatus.Error, loading = false)
                }
            } catch (e: HttpException) {
                setState {
                    it.copy(apiStatus = ApiStatus.Error, loading = false)
                }
            }
        }
    }

    private fun handleFile(imageFile: File) {
        viewModelScope.launch {
            runCatching {
                val receivedFile = appRepository.postPhoto(imageFile)
                val filePath = getFilePath(receivedFile)
                Log.d("message", filePath)
                setState {
                    it.copy(
                        apiStatus = ApiStatus.Success,
                        imagePathList = it.imagePathList + filePath,
                        loading = false
                    )
                }
            }.onFailure { error ->
                Log.e("error", error.message.orEmpty())
                when (error) {
                    is IOException -> setState {
                        it.copy(apiStatus = ApiStatus.Error, loading = false)
                    }

                    is HttpException -> setState {
                        it.copy(apiStatus = ApiStatus.Error, loading = false)
                    }

                    else -> setState { it.copy(loading = false) }
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
}