package com.tka.dwstart.jdbi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DateAsTimestampArgumentTest {

    private StatementContext context = mock(StatementContext.class);
    private PreparedStatement statement = mock(PreparedStatement.class);


    @Test
    public void should_accept_dates() {
        final DateAsTimestampArgument data = new DateAsTimestampArgument();
        boolean accept = data.accepts(Date.class, new Date(), context);
        assertThat(accept, is(true));
    }

    @Test
    public void should_build_date() throws SQLException {
        final DateAsTimestampArgument data = new DateAsTimestampArgument();
        final Argument argument = data.build(Date.class, new Date(), context);
        argument.apply(1, statement, context);
        verify(statement).setTimestamp(anyInt(), any(Timestamp.class));
    }

}
