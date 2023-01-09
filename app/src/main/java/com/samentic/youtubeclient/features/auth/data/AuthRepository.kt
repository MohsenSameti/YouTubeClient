package com.samentic.youtubeclient.features.auth.data

import android.content.SharedPreferences
import net.openid.appauth.AuthState
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

    companion object {
        const val KEY_AUTH_STATE = "authState"
    }
}