package com.samentic.youtubeclient.features.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.api.client.auth.oauth2.Credential
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class AuthRepository @Inject constructor(
    private val authState: AuthState,
    private val authorizationService: AuthorizationService,
    private val clientAuthentication: ClientAuthentication,
    private val prefs: SharedPreferences,
    private val credential: Credential
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

    suspend fun <T> ensureAccessToken(block: suspend () -> T): T {
        if (!authState.needsTokenRefresh) {
            credential.accessToken = authState.accessToken.orEmpty()
            return block()
        }
        // make sure two requests do not refresh token!
        mutex.withLock {
            // make sure another thread did not acquire a token
            if (!authState.needsTokenRefresh) {
                credential.accessToken = authState.accessToken.orEmpty()
                return block()
            }
            credential.accessToken = refreshToken()
            return block()
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