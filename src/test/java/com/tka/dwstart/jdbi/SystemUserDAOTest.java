package com.tka.dwstart.jdbi;

import java.sql.SQLException;
import java.util.Date;

import liquibase.exception.LiquibaseException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public final class SystemUserDAOTest extends TestWithLiquibase {

    private SystemUserDAO dao;

    @Before
    public void setup() throws LiquibaseException, SQLException, ClassNotFoundException {
        dao = jdbi.onDemand(SystemUserDAO.class);
    }

    @Test
    public void should_create_system_user() {
        dao.create("test@tester.com", "password");
        assertThat(dao.exists("test@tester.com"), is(true));
    }

    @Test
    public void should_load_system_user_by_id() {
        SystemUser user = dao.getById(1L);
        assertThat(user, not(is(nullValue())));
    }

    @Test
    public void should_load_system_user_by_email() {
        SystemUser user = dao.getByEmail("1@test.com");
        assertThat(user, not(is(nullValue())));
    }

    @Test
    public void should_update_last_login() {
        Date lastLogin = new Date();
        dao.updateLastLogin(1L, lastLogin);
        SystemUser user = dao.getById(1L);
        assertThat(user.getLastLogin().getTime(), is(lastLogin.getTime()));
    }

    @Test
    public void should_update_verified_email() {
        Date verifiedEmail = new Date();
        dao.verifyEmail(1L, verifiedEmail);
        SystemUser user = dao.getById(1L);
        assertThat(user.getEmailVerified().getTime(), is(verifiedEmail.getTime()));
    }

    @Test
    public void should_update_password() {
        dao.updatePassword(1L, "updated_password");
        SystemUser user = dao.getById(1L);
        assertThat(user.getPassword(), is("updated_password"));
    }

    @Test
    public void should_delete_system_user() {
        SystemUser user = dao.getById(2L);
        dao.delete(user.getId());
        assertThat(dao.exists(user.getEmail()), is(false));
    }

    @Test
    public void should_create_and_fetch_token() {
        dao.createToken(1L, TokenType.ACCESS_TOKEN.name(), "token", DateTime.now().plusDays(1).toDate());
        Token token = dao.getToken("token");
        assertThat(token, not(is(nullValue())));
        assertThat(token.getToken(), is("token"));
        assertThat(token.getUserId(), is(1L));
        assertThat(token.getTokenType(), is(TokenType.ACCESS_TOKEN));
    }

    @Test
    public void should_update_token_expire_date() {
        Date newExpireDate = DateTime.now().plusDays(1).toDate();
        dao.updateTokenExpireDate("token_to_be_updated", newExpireDate);
        assertThat(dao.getToken("token_to_be_updated").getExpires().getTime(), is(newExpireDate.getTime()));

    }
}
