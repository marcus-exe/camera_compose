package br.com.graest.camera.ui

import android.graphics.Bitmap
import br.com.graest.camera.model.Amphibian
import br.com.graest.camera.network.ApiStatus
import br.com.graest.retinografo.base.arch.UIState

data class MainUIState(
    val bitmaps: List<Bitmap>? = mutableListOf(),
    val bitmapIndex: Int? = null,
    val amphibianList : List<Amphibian> = mutableListOf(),
    val amphibian: Amphibian? = null,
    val apiStatus: ApiStatus = ApiStatus.Loading,
    val imagePathList: List<String> = emptyList(),
    val loading: Boolean = false
) : UIState