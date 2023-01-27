package com.samentic.youtubeclient.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samentic.youtubeclient.features.auth.ui.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        SubscriptionViewModelModule::class,
        PlaylistViewModelModule::class
    ]
)
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: YoutubeViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

}