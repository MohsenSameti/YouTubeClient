package com.samentic.youtubeclient.core.di.viewmodel

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.susbcription.ui.SubscriptionListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SubscriptionViewModelModule {
    @IntoMap
    @ViewModelKey(SubscriptionListViewModel::class)
    @Binds
    abstract fun bindSubscriptionListViewModel(viewModel: SubscriptionListViewModel): ViewModel
}