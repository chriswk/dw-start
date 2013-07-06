package com.tka.dwstart.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AccessToken {
    @JsonProperty
    private final String accessToken;
    @JsonProperty
    private final long expiresIn;

    public AccessToken(final String accessToken, final long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
