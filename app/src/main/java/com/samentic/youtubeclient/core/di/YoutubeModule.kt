package com.samentic.youtubeclient.core.di

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.Credential.AccessMethod
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.samentic.youtubeclient.core.data.AuthorizationHeaderAccessMethod
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object YoutubeModule {

    @Provides
    fun provideCredentialAccessMethod(): AccessMethod {
        return AuthorizationHeaderAccessMethod()
    }

    @Singleton
    @Provides
    fun provideCredential(
        accessMethod: AccessMethod
    ): Credential {
        return Credential.Builder(accessMethod)
            .build()
    }

    @Provides
    fun provideHttpTransport(): HttpTransport {
        return GoogleNetHttpTransport.newTrustedTransport()
    }

    @Provides
    fun provideGsonFactory(): JsonFactory {
        return GsonFactory.getDefaultInstance()
    }

    @Singleton
    @Provides
    fun provideYoutube(
        transport: HttpTransport,
        jsonFactory: JsonFactory,
        credential: Credential
    ): YouTube {
        return YouTube.Builder(transport, jsonFactory, credential)
            .setApplicationName("Samentic Test")
            .build()
    }
}