package com.samentic.youtubeclient.core.di.viewmodel

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.playlist.ui.PlaylistItemsViewModel
import com.samentic.youtubeclient.features.playlist.ui.PlaylistsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlaylistViewModelModule {
    @IntoMap
    @ViewModelKey(PlaylistsViewModel::class)
    @Binds
    abstract fun bindPlaylistsViewModel(viewModel: PlaylistsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PlaylistItemsViewModel::class)
    @Binds
    abstract fun bindPlaylistItemsViewModel(viewModel: PlaylistItemsViewModel): ViewModel
}