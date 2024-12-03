package br.com.graest.camera.ui

import br.com.graest.retinografo.base.arch.UIEffect

sealed interface MainEffect : UIEffect {
    data object GoToLocalImageDetails : MainEffect
    data object GoToCloudImageDetails: MainEffect
    data object SendImageCloud: MainEffect
}