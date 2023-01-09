package com.samentic.youtubeclient

import android.app.Application
import com.samentic.youtubeclient.core.di.AppComponent
import com.samentic.youtubeclient.core.di.DaggerAppComponent

class YoutubeApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}