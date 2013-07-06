package com.tka.dwstart.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static com.tka.dwstart.util.PasswordUtil.createHash;
import static com.tka.dwstart.util.PasswordUtil.validatePassword;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class PasswordUtilTest {

    @Test
    public void should_generate_unique_and_valid_password_hashes() {

        Set<String> passwords = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String password =  ""+i;
            String hash = createHash(password);
            String secondHash = createHash(password);

            assertThat(hash, not(is(secondHash)));
            assertThat(validatePassword(password, hash), is(true));
            assertThat(validatePassword(password + "1", hash), is(false));
            passwords.add(hash);

        }
        assertThat(passwords.size(), is(100));
    }
}
