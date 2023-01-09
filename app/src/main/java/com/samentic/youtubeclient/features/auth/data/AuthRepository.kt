package com.samentic.youtubeclient.features.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authState: AuthState,
    private val prefs: SharedPreferences
) {

    fun isAuthorized(): Boolean {
        return authState.isAuthorized
    }

    fun updateAuthState(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) {
        authState.update(authResponse, authException)
        prefs.edit {
            putString(KEY_AUTH_STATE, authState.jsonSerializeString())
        }
    }

    fun updateAuthState(tokenResponse: TokenResponse?, authException: AuthorizationException?) {
        authState.update(tokenResponse, authException)
        prefs.edit {
            putString(KEY_AUTH_STATE, authState.jsonSerializeString())
        }
    }

    companion object {
        const val KEY_AUTH_STATE = "authState"
    }
}