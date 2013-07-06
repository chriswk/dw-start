package com.tka.dwstart.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LoggedInUser {

    @JsonProperty
    private final String email;

    public LoggedInUser(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
