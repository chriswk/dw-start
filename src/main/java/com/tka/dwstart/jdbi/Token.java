package com.tka.dwstart.jdbi;

import javax.validation.constraints.NotNull;
import java.util.Date;

import org.joda.time.DateTime;

public final class Token {

    @NotNull
    private final long id;

    @NotNull
    private final TokenType tokenType;

    private final long userId;

    @NotNull
    private final String token;

    @NotNull
    private final Date created;

    @NotNull
    private final Date expires;

    public Token(final long id,
          final long userId,
          final TokenType tokenType,
          final String token,
          final Date created,
          final Date expires) {
        this.id = id;
        this.tokenType = tokenType;
        this.userId = userId;
        this.token = token;
        this.created = created;
        this.expires = expires;
    }

    public long getId() {
        return id;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public Date getCreated() {
        return created;
    }

    public Date getExpires() {
        return expires;
    }

    public boolean isExpired() {
        return new DateTime(expires).isBeforeNow();
    }
}
