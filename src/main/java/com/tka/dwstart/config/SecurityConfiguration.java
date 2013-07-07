package com.tka.dwstart.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;

public final class SecurityConfiguration {

    private static final long DEFAULT_TOKEN_TIMEOUT = 60000L;
    private static final String DEFAULT_CACHE_BUILDER_SPEC = "maximumSize=1000, expireAfterAccess=1m";

    @Valid @NotNull @JsonProperty
    private CacheBuilderSpec authenticationCachePolicy = CacheBuilderSpec.parse(DEFAULT_CACHE_BUILDER_SPEC);

    @Valid @NotNull @JsonProperty
    private long defaultTokenTimeout = DEFAULT_TOKEN_TIMEOUT;

    public CacheBuilderSpec getAuthenticationCachePolicy() {
        return authenticationCachePolicy;
    }

    public long getDefaultTokenTimeout() {
        return defaultTokenTimeout;
    }

}
