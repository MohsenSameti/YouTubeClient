package com.samentic.youtubeclient.features.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.openid.appauth.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class AuthRepository @Inject constructor(
    private val authState: AuthState,
    private val authorizationService: AuthorizationService,
    private val clientAuthentication: ClientAuthentication,
    private val prefs: SharedPreferences
) {
    private val mutex = Mutex()

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

    suspend fun <T> ensureAccessToken(block: (accessToken: String) -> T): T {
        if (!authState.needsTokenRefresh) {
            return block(authState.accessToken.orEmpty())
        }
        // make sure two requests do not refresh token!
        mutex.withLock {
            if(!authState.needsTokenRefresh) {
                return block(authState.accessToken.orEmpty())
            }
            return block(refreshToken())
        }
    }

    private suspend fun refreshToken(): String {
        return suspendCancellableCoroutine<String> {
            try {
                authState.performActionWithFreshTokens(
                    authorizationService,
                    clientAuthentication
                ) { accessToken, idToken, ex ->
                    prefs.edit { putString(KEY_AUTH_STATE, authState.jsonSerializeString()) }

                    if (ex == null) {
                        if (accessToken != null)
                            it.resume(accessToken)
                        else
                            it.resumeWithException(NullPointerException("null accessToken with no error"))
                    } else {
                        it.resumeWithException(ex)
                    }

                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                it.resumeWithException(e)
            }
        }
    }

    companion object {
        const val KEY_AUTH_STATE = "authState"
    }
}