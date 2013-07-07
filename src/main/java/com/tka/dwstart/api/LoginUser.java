package com.tka.dwstart.api;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

public final class LoginUser {

    @NotNull @Email
    private String email;

    @NotNull
    private String password;

    @JsonCreator
    public LoginUser(@JsonProperty("email") final String email,
                     @JsonProperty("password") final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
