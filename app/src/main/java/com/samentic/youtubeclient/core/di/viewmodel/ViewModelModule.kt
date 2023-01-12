package com.samentic.youtubeclient.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samentic.youtubeclient.features.auth.ui.AuthViewModel
import com.samentic.youtubeclient.features.playlist.ui.PlaylistsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: YoutubeViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PlaylistsViewModel::class)
    @Binds
    abstract fun bindPlaylistsViewModel(viewModel: PlaylistsViewModel): ViewModel

}