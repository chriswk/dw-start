package com.tka.dwstart.jdbi;

import javax.validation.constraints.NotNull;
import java.util.Date;

import org.hibernate.validator.constraints.Email;

public final class SystemUser {

    @NotNull
    private final long id;

    @NotNull
    private final Date created;

    @NotNull @Email
    private final String email;

    @NotNull
    private final String password;

    private final Date lastLogin;
    private final Date emailVerified;

    public SystemUser(final long id,
               final Date created,
               final String email,
               final String password,
               final Date emailVerified,
               final Date lastLogin) {
        this.id = id;
        this.created = created;
        this.email = email;
        this.password = password;
        this.emailVerified = emailVerified;
        this.lastLogin = lastLogin;
    }

    public long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public Date getEmailVerified() {
        return emailVerified;
    }
}
