package com.tka.dwstart.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

public final class NewUser {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_FIELD_LENGTH = 255;

    @NotNull
    @Email
    @Size(max = MAX_FIELD_LENGTH)
    private String email;

    @NotNull @Size(min = MIN_PASSWORD_LENGTH, max = MAX_FIELD_LENGTH)
    private String password;

    @NotNull
    private String confirmPassword;

    @JsonCreator
    public NewUser(@JsonProperty("email") final String email,
                   @JsonProperty("password") final String password,
                   @JsonProperty("confirmPassword") final String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public boolean isPasswordConfirmed() {
        return password.equals(confirmPassword);
    }
}
