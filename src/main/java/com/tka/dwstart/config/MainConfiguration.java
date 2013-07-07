package com.tka.dwstart.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public final class MainConfiguration extends Configuration {

    @Valid @NotNull @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    @Valid @NotNull @JsonProperty
    private CORSConfiguration cors = new CORSConfiguration();

    @Valid @NotNull @JsonProperty
    private SecurityConfiguration security  = new SecurityConfiguration();

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }

    public CORSConfiguration getCORSConfiguration() {
        return cors;
    }

    public SecurityConfiguration getSecurityConfiguration() {
        return security;
    }
}
