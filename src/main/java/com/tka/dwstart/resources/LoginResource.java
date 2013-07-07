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
import java.util.Date;

import com.tka.dwstart.api.AccessToken;
import com.tka.dwstart.api.LoginUser;
import com.tka.dwstart.jdbi.SystemUser;
import com.tka.dwstart.jdbi.SystemUserDAO;
import com.tka.dwstart.jdbi.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tka.dwstart.util.PasswordUtil.validatePassword;
import static com.tka.dwstart.util.TokenUtil.createToken;
import static com.tka.dwstart.util.TokenUtil.getExpiresFromNow;

@Path("/user/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class LoginResource {

    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    private final SystemUserDAO systemUserDAO;
    private final long tokenTimout;

    public LoginResource(final SystemUserDAO systemUserDAO, final long tokenTimout) {
        this.systemUserDAO = systemUserDAO;
        this.tokenTimout = tokenTimout;
    }

    @POST
    public AccessToken login(@Valid final LoginUser loginUser)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        SystemUser systemUser = systemUserDAO.getByEmail(loginUser.getEmail());
        if (systemUser != null && validatePassword(loginUser.getPassword(), systemUser.getPassword())) {
            systemUserDAO.begin();

            String token = createToken();
            systemUserDAO.createToken(systemUser.getId(), TokenType.ACCESS_TOKEN.name(), token, getExpiresFromNow(tokenTimout));
            systemUserDAO.updateLastLogin(systemUser.getId(), new Date());

            systemUserDAO.commit();

            LOG.info("Loggged in user: {}", systemUser.getEmail());
            return new AccessToken(token, tokenTimout);
        } else {
            if (systemUser == null) {
                LOG.warn("SystemUser does not exists: {}", loginUser.getEmail());
            } else {
                LOG.warn("Email/Password is not valid: {}", loginUser.getEmail());
            }
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }

}
