package com.samentic.youtubeclient.features.auth.ui

import androidx.lifecycle.ViewModel
import com.samentic.youtubeclient.features.auth.data.AuthRepository
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun isAuthorized() = authRepository.isAuthorized()
}