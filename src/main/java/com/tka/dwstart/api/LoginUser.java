package com.tka.dwstart.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public final class LoginUser {

    @NotNull @Email
    private String email;

    @NotNull
    private String password;

    LoginUser() { }

    public LoginUser(final String email, final String password) {
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
