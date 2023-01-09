package com.samentic.youtubeclient.features.auth.data

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.samentic.youtubeclient.BuildConfig
import com.samentic.youtubeclient.features.auth.data.AuthRepository.Companion.KEY_AUTH_STATE
import dagger.Module
import dagger.Provides
import net.openid.appauth.*
import javax.inject.Singleton

// TODO: Separate AuthComponent from AppComponent
@Module
object AuthModule {

    @Provides
    fun provideClientSecretBasic(): ClientAuthentication {
        return ClientSecretBasic(BuildConfig.CLIENT_SECRET)
    }

    @Provides
    fun provideAuthorizationService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }

    @Provides
    fun provideAuthorizationServiceConfiguration(): AuthorizationServiceConfiguration {
        // TODO: later take this in AuthComponent
        return AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.google.com/o/oauth2/auth"),
            Uri.parse("https://oauth2.googleapis.com/token")
        )
    }

    @Singleton
    @Provides
    fun provideAuthState(pref: SharedPreferences): AuthState {
        val authState = pref.getString(KEY_AUTH_STATE, null)?.let {
            AuthState.jsonDeserialize(it)
        }
        return authState ?: AuthState()
    }
}