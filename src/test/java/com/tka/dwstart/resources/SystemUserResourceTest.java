package com.tka.dwstart.resources;

import java.util.Date;

import com.tka.dwstart.api.LoggedInUser;
import com.tka.dwstart.jdbi.SystemUser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class SystemUserResourceTest {

    @Test
    public void should_return_logged_in_user() {
        final LoggedInUser user = new SystemUserResource()
                .getUser(new SystemUser(0, new Date(),"tester@test.com", "super$ecret", null, null));
        assertThat(user, not(nullValue()));
        assertThat(user.getEmail(), is("tester@test.com"));
    }
}
