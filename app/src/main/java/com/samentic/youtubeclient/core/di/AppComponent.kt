package com.samentic.youtubeclient.core.di

import com.samentic.youtubeclient.YoutubeApplication
import com.samentic.youtubeclient.core.di.viewmodel.ViewModelModule
import com.samentic.youtubeclient.features.auth.data.AuthModule
import com.samentic.youtubeclient.features.auth.ui.AuthFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class, ContextModule::class, LocalModule::class,
        AuthModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: AuthFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: YoutubeApplication
        ): AppComponent
    }
}