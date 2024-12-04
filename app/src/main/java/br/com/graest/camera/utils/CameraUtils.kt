package br.com.graest.camera.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

private const val CHILD_DIR = "image_files"
object CameraUtils {

    suspend fun saveBitmapToExternalStorage(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
        onFileCreated: (String) -> Unit,
    ): File {
        return withContext(Dispatchers.IO) {
            val directory = File(context.cacheDir, CHILD_DIR)
            if (!directory.exists()) directory.mkdirs()
            val file = File.createTempFile("ret_", ".png", directory)
            onFileCreated(file.absolutePath)

            runCatching {
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }
            }
            return@withContext file
        }
    }


    public fun takePhoto(
        applicationContext: Context,
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object: OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    val newWith = (300.0 / image.height) * image.width
                    val scaledBitmap = Bitmap.createScaledBitmap(rotatedBitmap, newWith.toInt(), 300, false)

                    onPhotoTaken(scaledBitmap)
                    image.close()
                    Log.d("Image", "Foto Tirada")
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo: ", exception)
                }
            }
        )
    }
}