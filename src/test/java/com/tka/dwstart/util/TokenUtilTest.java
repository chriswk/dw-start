package com.tka.dwstart.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TokenUtilTest {

    @Test
    public void should_create_unique_tokens() {
        final Set<String> tokens = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            tokens.add(TokenUtil.createToken());
        }
        assertThat(tokens.size(), is(100));
    }

    @Test
    public void should_get_expiry_date() {
        final DateTime now = DateTime.now();
        final Date expiry = TokenUtil.getExpiresFromNow(1000);
        final int interval = Seconds.secondsBetween(now, new DateTime(expiry)).getSeconds();
        assertThat(interval, is(1));

    }

}
