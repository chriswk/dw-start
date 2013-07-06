package com.tka.dwstart.resources;

import javax.ws.rs.WebApplicationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import com.tka.dwstart.api.AccessToken;
import com.tka.dwstart.api.NewUser;
import com.tka.dwstart.jdbi.SystemUserDAO;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterResourceTest {

    private SystemUserDAO systemUserDAO = mock(SystemUserDAO.class);
    private RegisterResource registerResource = new RegisterResource(systemUserDAO, 100L);
    private NewUser user = new NewUser("tester@test.com", "super$ecret", "super$ecret");

    @Test
    public void should_register_user() throws InvalidKeySpecException, NoSuchAlgorithmException {
        when(systemUserDAO.exists(user.getEmail())).thenReturn(false);
        AccessToken token = registerResource.register(user);

        verify(systemUserDAO).exists(user.getEmail());
        verify(systemUserDAO).create(anyString(), anyString());
        verify(systemUserDAO).createToken(anyLong(), anyString(), anyString(), any(Date.class));

        assertThat(token, not(nullValue()));
        assertThat(token.getAccessToken(), not(nullValue()));
        assertThat(token.getExpiresIn(), is(100L));
    }

    @Test(expected = WebApplicationException.class)
    public void should_not_register_user_if_password_is_not_confirmed() throws InvalidKeySpecException, NoSuchAlgorithmException {
        registerResource.register(new NewUser("tester@test.com", "super$ecret", ""));
    }

    @Test(expected = WebApplicationException.class)
    public void should_not_register_user_if_user_is_already_registered() throws InvalidKeySpecException, NoSuchAlgorithmException {
        when(systemUserDAO.exists(user.getEmail())).thenReturn(true);
        registerResource.register(user);
    }

}
