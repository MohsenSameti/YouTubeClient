package com.samentic.youtubeclient.core.di

import android.app.Application
import android.content.Context
import com.samentic.youtubeclient.YoutubeApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ContextModule {

    @Binds
    abstract fun bindApplication(application: YoutubeApplication): Application

    companion object {
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }
    }
}