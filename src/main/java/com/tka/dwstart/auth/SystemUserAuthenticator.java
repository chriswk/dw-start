package com.tka.dwstart.auth;

import com.tka.dwstart.jdbi.SystemUser;
import com.tka.dwstart.jdbi.SystemUserDAO;
import com.tka.dwstart.jdbi.Token;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

import static com.tka.dwstart.util.TokenUtil.getExpiresFromNow;

public final class SystemUserAuthenticator implements Authenticator<String, SystemUser> {

    private final SystemUserDAO systemUserDAO;
    private final long tokenTimeout;

    public SystemUserAuthenticator(final SystemUserDAO systemUserDAO, final long tokenTimeout) {
        this.systemUserDAO = systemUserDAO;
        this.tokenTimeout = tokenTimeout;
    }

    @Override
    public Optional<SystemUser> authenticate(final String credentials) throws AuthenticationException {
        Token token = systemUserDAO.getToken(credentials);
        if (token != null && !token.isExpired()) {
            systemUserDAO.updateTokenExpireDate(credentials, getExpiresFromNow(tokenTimeout));
            return Optional.fromNullable(systemUserDAO.getById(token.getUserId()));
        } else {
            return Optional.absent();
        }
    }
}
