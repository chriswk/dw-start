package com.tka.dwstart.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public final class TokenResultMapper implements ResultSetMapper<Token> {
    @Override
    public Token map(final int index, final ResultSet r, final StatementContext ctx) throws SQLException {
        return new Token(
                r.getLong("id"),
                r.getLong("userid"),
                TokenType.valueOf(r.getString("tokentype")),
                r.getString("token"),
                r.getTimestamp("created"),
                r.getTimestamp("expires"));
    }
}
