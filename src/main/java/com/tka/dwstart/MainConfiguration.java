package com.tka.dwstart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public final class MainConfiguration extends Configuration {

    private static final long DEFAULT_TOKEN_TIMEOUT = 60000L;

    @Valid
    @NotNull
    @JsonProperty
    private CacheBuilderSpec authenticationCachePolicy;


    @Valid
    @NotNull
    @JsonProperty
    private long defaultTokenTimeout = DEFAULT_TOKEN_TIMEOUT;

    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public long getDefaultTokenTimeout() {
        return defaultTokenTimeout;
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }

    public CacheBuilderSpec getAuthenticationCachePolicy() {
        return authenticationCachePolicy;
    }
}
