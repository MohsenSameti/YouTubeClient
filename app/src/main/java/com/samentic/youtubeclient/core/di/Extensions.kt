package com.samentic.youtubeclient.core.di

import androidx.fragment.app.Fragment
import com.samentic.youtubeclient.YoutubeApplication

fun Fragment.findAppComponent(): AppComponent {
    return (requireActivity().application as YoutubeApplication).appComponent
}