package com.samentic.youtubeclient.features.auth.data

import android.content.SharedPreferences
import com.samentic.youtubeclient.features.auth.data.AuthRepository.Companion.KEY_AUTH_STATE
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthState
import javax.inject.Singleton

@Module
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthState(pref: SharedPreferences): AuthState {
        val authState = pref.getString(KEY_AUTH_STATE, null)?.let {
            AuthState.jsonDeserialize(it)
        }
        return authState ?: AuthState()
    }
}