package com.tka.dwstart.auth;

import java.util.Date;

import com.google.common.base.Optional;
import com.tka.dwstart.jdbi.SystemUser;
import com.tka.dwstart.jdbi.SystemUserDAO;
import com.tka.dwstart.jdbi.Token;
import com.tka.dwstart.jdbi.TokenType;
import com.yammer.dropwizard.auth.AuthenticationException;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class SystemUserAuthenticatorTest {

    private final SystemUserDAO systemUserDAO = mock(SystemUserDAO.class);
    private final SystemUserAuthenticator authenticator = new SystemUserAuthenticator(systemUserDAO, 100L);
    private final String token = "some_authentication_token";
    private final Date yesterday =  DateTime.now().minusDays(1).toDate();
    private final Date tomorrow =  DateTime.now().plusDays(1).toDate();

    @Test
    public void should_get_valid_user() throws AuthenticationException {

        when(systemUserDAO.getToken(token)).thenReturn(
            new Token(1, 1, TokenType.ACCESS_TOKEN, token, yesterday, tomorrow));
        when(systemUserDAO.getById(1)).thenReturn(new SystemUser(1, yesterday, "tester@test.com", "abc", null, new Date()));

        Optional<SystemUser> user = authenticator.authenticate(token);

        verify(systemUserDAO).getToken(token);
        verify(systemUserDAO).getById(1);
        assertThat(user.isPresent(), is(true));
    }

    @Test
    public void should_throw_exeption_if_we_can_not_fetch_user() throws AuthenticationException {
        when(systemUserDAO.getToken(token)).thenReturn(
                new Token(1, 1, TokenType.ACCESS_TOKEN, token, yesterday, tomorrow));
        when(systemUserDAO.getById(1)).thenReturn(null);

        Optional<SystemUser> user = authenticator.authenticate(token);
        verify(systemUserDAO).getToken(token);
        verify(systemUserDAO).getById(1);
        assertThat(user.isPresent(), is(false));
    }


    @Test
    public void should_not_authenticate_expired_token() throws AuthenticationException {
        when(systemUserDAO.getToken(token)).thenReturn(
                new Token(1, 1, TokenType.ACCESS_TOKEN, token, yesterday, yesterday));

        Optional<SystemUser> user = authenticator.authenticate(token);
        verify(systemUserDAO).getToken(token);
        verifyNoMoreInteractions(systemUserDAO);
        assertThat(user.isPresent(), is(false));
    }

    @Test
    public void should_not_authenticate_invalid_token() throws AuthenticationException {
        when(systemUserDAO.getToken(token)).thenReturn(null);

        Optional<SystemUser> user = authenticator.authenticate(token);
        verify(systemUserDAO).getToken(token);
        verifyNoMoreInteractions(systemUserDAO);
        assertThat(user.isPresent(), is(false));
    }


}
