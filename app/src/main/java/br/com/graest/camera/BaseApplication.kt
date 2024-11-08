package br.com.graest.camera

import android.app.Application
import br.com.graest.camera.data.AppContainer
import br.com.graest.camera.data.AppRepository
import br.com.graest.camera.data.DefaultAppContainer

class BaseApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}