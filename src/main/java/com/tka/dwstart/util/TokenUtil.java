package com.tka.dwstart.util;

import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;

public final class TokenUtil {

    private TokenUtil() { }

    public static Date getExpiresFromNow(final long tokenTimeout) {
        return DateTime.now().plusMillis((int) tokenTimeout).toDate();
    }

    public static String createToken() {
        return UUID.randomUUID().toString();
    }


}
