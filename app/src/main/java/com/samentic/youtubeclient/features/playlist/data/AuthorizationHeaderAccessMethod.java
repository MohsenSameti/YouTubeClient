package com.samentic.youtubeclient.features.playlist.data;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;
import java.util.List;

// From google-api-client libraries
public class AuthorizationHeaderAccessMethod implements Credential.AccessMethod {
    /**
     * Authorization header prefix.
     */
    static final String HEADER_PREFIX = "Bearer ";

    public AuthorizationHeaderAccessMethod() {
    }

    public void intercept(HttpRequest request, String accessToken) throws IOException {
        request.getHeaders().setAuthorization(HEADER_PREFIX + accessToken);
    }

    public String getAccessTokenFromRequest(HttpRequest request) {
        List<String> authorizationAsList = request.getHeaders().getAuthorizationAsList();
        if (authorizationAsList != null) {
            for (String header : authorizationAsList) {
                if (header.startsWith(HEADER_PREFIX)) {
                    return header.substring(HEADER_PREFIX.length());
                }
            }
        }
        return null;
    }
}