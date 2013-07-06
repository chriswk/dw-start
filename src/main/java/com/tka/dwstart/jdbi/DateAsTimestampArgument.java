package com.tka.dwstart.jdbi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

public final class DateAsTimestampArgument implements ArgumentFactory<Date> {
    @Override
    public boolean accepts(final Class<?> expectedType, final Object value, final StatementContext ctx) {
        return value != null && java.util.Date.class.isAssignableFrom(value.getClass());
    }

    @Override
    public Argument build(final Class<?> expectedType, final Date value,  final StatementContext ctx) {
        return new Argument() {
            @Override
            public void apply(final int position, final PreparedStatement statement, final StatementContext ctx) throws SQLException {
                statement.setTimestamp(position, new java.sql.Timestamp(value.getTime()));
            }
        };
    }
}
