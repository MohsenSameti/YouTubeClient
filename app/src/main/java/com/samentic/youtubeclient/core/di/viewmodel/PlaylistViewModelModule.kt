package com.samentic.youtubeclient.core.di.viewmodel

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.playlist.ui.PlaylistDetailViewModel
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
    @ViewModelKey(PlaylistDetailViewModel::class)
    @Binds
    abstract fun bindPlaylistItemsViewModel(viewModel: PlaylistDetailViewModel): ViewModel
}