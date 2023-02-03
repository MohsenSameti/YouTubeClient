package com.samentic.youtubeclient.core.di.viewmodel

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.channel.ui.ChannelDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChannelViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChannelDetailViewModel::class)
    abstract fun bindChannelDetailViewModel(viewModel: ChannelDetailViewModel): ViewModel
}