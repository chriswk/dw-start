package com.tka.dwstart.jdbi;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SystemUserResultMapperTest {

    private final StatementContext context = mock(StatementContext.class);
    private final ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void should_map_to_system_user() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getDate("created")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getString("email")).thenReturn("test@tester.com");
        when(resultSet.getString("password")).thenReturn("super$ecret");
        when(resultSet.getDate("emailverified")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getDate("lastlogin")).thenReturn(new Date(System.currentTimeMillis()));

        final SystemUser user = new SystemUserResultMapper().map(1, resultSet,context);

        assertThat(user.getId(), is(1L));
        assertThat(user.getCreated(), not(is(nullValue())));
        assertThat(user.getEmail(), is("test@tester.com"));
        assertThat(user.getPassword(), is("super$ecret"));
        assertThat(user.getEmailVerified(), not(is(nullValue())));
        assertThat(user.getLastLogin(), not(is(nullValue())));

    }
}
