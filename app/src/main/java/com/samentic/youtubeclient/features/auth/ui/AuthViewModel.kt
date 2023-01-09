package com.samentic.youtubeclient.features.auth.ui

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenResponse
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun isAuthorized() = authRepository.isAuthorized()

    fun updateAuthState(
        authResponse: AuthorizationResponse?,
        authException: AuthorizationException?
    ) {
        authRepository.updateAuthState(authResponse, authException)
    }

    fun updateAuthState(tokenResponse: TokenResponse?, authException: AuthorizationException?) {
        authRepository.updateAuthState(tokenResponse, authException)
    }
}