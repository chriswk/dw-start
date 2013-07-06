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

public class TokenResultMapperTest {

    private final StatementContext context = mock(StatementContext.class);
    private final ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void should_map_token() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getLong("userid")).thenReturn(1L);
        when(resultSet.getDate("created")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getString("token")).thenReturn("token");
        when(resultSet.getString("tokentype")).thenReturn(TokenType.ACCESS_TOKEN.name());
        when(resultSet.getDate("expires")).thenReturn(new Date(System.currentTimeMillis()));


        final Token token = new TokenResultMapper().map(1, resultSet,context);

        assertThat(token.getId(), is(1L));
        assertThat(token.getCreated(), not(is(nullValue())));
        assertThat(token.getToken(), is("token"));
        assertThat(token.getTokenType(), is(TokenType.ACCESS_TOKEN));
        assertThat(token.getExpires(), not(is(nullValue())));
    }
}
