package com.tka.dwstart.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tka.dwstart.api.LoggedInUser;
import com.tka.dwstart.jdbi.SystemUser;
import com.yammer.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public final class SystemUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(SystemUserResource.class);

    @GET
    public LoggedInUser getUser(@Auth final SystemUser user) {
        LOG.debug("Getting systemUser: {}", user.getEmail());
        return new LoggedInUser(user.getEmail());
    }

}
