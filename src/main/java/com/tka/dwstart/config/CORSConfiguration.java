package com.tka.dwstart.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CORSConfiguration {

    @Valid @NotNull @JsonProperty
    private String allowedOrigins = "*";
    @Valid @NotNull @JsonProperty
    private String allowedMethods = "GET,POST,HEAD";
    @Valid @NotNull @JsonProperty
    private String allowedHeaders = "X-Requested-With,Content-Type,Accept,Origin";
    @Valid @NotNull @JsonProperty
    private boolean allowedCredentials = false;

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public String getAllowedMethods() {
        return allowedMethods;
    }

    public String getAllowedHeaders() {
        return allowedHeaders;
    }

    public String getAllowedCredentials() {
        return String.valueOf(allowedCredentials);
    }
}
