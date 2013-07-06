package com.tka.dwstart.resources;

import javax.ws.rs.WebApplicationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import com.tka.dwstart.api.AccessToken;
import com.tka.dwstart.api.LoginUser;
import com.tka.dwstart.jdbi.SystemUser;
import com.tka.dwstart.jdbi.SystemUserDAO;
import org.junit.Test;

import static com.tka.dwstart.util.PasswordUtil.createHash;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginResourceTest {

    private SystemUserDAO systemUserDAO = mock(SystemUserDAO.class);
    private LoginResource resource = new LoginResource(systemUserDAO, 100L);
    private LoginUser loginUser = new LoginUser("tester@test.com", "super$ecret");

    @Test
    public void should_login_if_password_is_correct() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String correctPassword = createHash(loginUser.getPassword());
        SystemUser user = createUser(loginUser.getEmail(), correctPassword);
        when(systemUserDAO.getByEmail(loginUser.getEmail())).thenReturn(user);

        AccessToken response = resource.login(loginUser);

        verify(systemUserDAO).getByEmail(loginUser.getEmail());
        assertThat(response, not(nullValue()));
        assertThat(response.getAccessToken(), not(nullValue()));
        assertThat(response.getExpiresIn(), is(100L));
    }

    @Test (expected = WebApplicationException.class)
    public void should_not_validate_if_user_does_not_exist() throws InvalidKeySpecException, NoSuchAlgorithmException {
        when(systemUserDAO.getByEmail(loginUser.getEmail())).thenReturn(null);
        resource.login(loginUser);
    }

    @Test (expected = WebApplicationException.class)
    public void should_not_validate_if_wrong_password() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String correctPassword = createHash("correctPassword");
        SystemUser user = createUser(loginUser.getEmail(), correctPassword);
        when(systemUserDAO.getByEmail(loginUser.getEmail())).thenReturn(user);
        resource.login(loginUser);
    }

    private SystemUser createUser(String email, String password) {
        return new SystemUser(0, new Date(), email, password, null, null);
    }


}
