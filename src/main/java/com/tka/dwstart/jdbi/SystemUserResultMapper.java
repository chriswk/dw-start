package com.tka.dwstart.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public final class SystemUserResultMapper implements ResultSetMapper<SystemUser> {

    @Override
    public SystemUser map(final int index, final ResultSet r, final StatementContext ctx) throws SQLException {
        return new SystemUser(
                r.getLong("id"),
                r.getTimestamp("created"),
                r.getString("email"),
                r.getString("password"),
                r.getTimestamp("emailverified"),
                r.getTimestamp("lastlogin"));
    }
}
