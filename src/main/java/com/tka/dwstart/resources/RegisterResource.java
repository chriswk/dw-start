package com.tka.dwstart.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.tka.dwstart.api.AccessToken;
import com.tka.dwstart.api.NewUser;
import com.tka.dwstart.jdbi.SystemUserDAO;
import com.tka.dwstart.jdbi.TokenType;
import com.tka.dwstart.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tka.dwstart.util.TokenUtil.createToken;
import static com.tka.dwstart.util.TokenUtil.getExpiresFromNow;

@Path("/user/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class RegisterResource {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterResource.class);
    private final SystemUserDAO systemUserDAO;
    private final long tokenTimeout;

    public RegisterResource(final SystemUserDAO systemUserDAO, final long tokenTimeout) {
        this.systemUserDAO = systemUserDAO;
        this.tokenTimeout = tokenTimeout;
    }

    @POST
    public AccessToken register(@Valid final NewUser newUser) throws InvalidKeySpecException, NoSuchAlgorithmException {

        if (newUser.isPasswordConfirmed() && !systemUserDAO.exists(newUser.getEmail()))   {
            systemUserDAO.begin();

            long userId = systemUserDAO.create(newUser.getEmail(), PasswordUtil.createHash(newUser.getPassword()));
            String token = createToken();
            systemUserDAO.createToken(userId, TokenType.ACCESS_TOKEN.name(), token, getExpiresFromNow(tokenTimeout));

            systemUserDAO.commit();

            LOG.info("Registered user: {}", newUser.getEmail());
            return new AccessToken(token, tokenTimeout);
        } else {
            if (!newUser.isPasswordConfirmed()) {
                LOG.warn("Password is not confirmed for user: {}", newUser.getEmail());
            } else {
                LOG.warn("User is already registered: {}", newUser.getEmail());
            }
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        }
    }

}
