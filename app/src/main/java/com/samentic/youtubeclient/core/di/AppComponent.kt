package com.samentic.youtubeclient.core.di

import com.samentic.youtubeclient.YoutubeApplication
import com.samentic.youtubeclient.core.di.viewmodel.ViewModelModule
import com.samentic.youtubeclient.features.auth.data.AuthModule
import com.samentic.youtubeclient.features.auth.ui.AuthFragment
import com.samentic.youtubeclient.features.channel.ui.ChannelDetailFragment
import com.samentic.youtubeclient.features.playlist.ui.PlaylistsFragment
import com.samentic.youtubeclient.features.playlist.ui.PlaylistDetailFragment
import com.samentic.youtubeclient.features.susbcription.ui.SubscriptionListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class, ContextModule::class, LocalModule::class,
        AuthModule::class, YoutubeModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: AuthFragment)
    fun inject(fragment: PlaylistsFragment)
    fun inject(fragment: PlaylistDetailFragment)

    fun inject(fragment: SubscriptionListFragment)

    fun inject(fragment: ChannelDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: YoutubeApplication
        ): AppComponent
    }
}