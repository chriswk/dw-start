package com.tka.dwstart;

import com.tka.dwstart.auth.SystemUserAuthenticator;
import com.tka.dwstart.config.MainConfiguration;
import com.tka.dwstart.filters.UTF8EncodingFilter;
import com.tka.dwstart.jdbi.DateAsTimestampArgument;
import com.tka.dwstart.jdbi.SystemUserDAO;
import com.tka.dwstart.resources.AuthenticationResource;
import com.tka.dwstart.resources.RegistrationResource;
import com.tka.dwstart.resources.SystemUserResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.CachingAuthenticator;
import com.yammer.dropwizard.auth.oauth.OAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MainService extends Service<MainConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(MainService.class);

    public static void main(final String[] args) throws Exception {
        LOG.info("Staring service with arguments: {} {}", args[0], args[1]);
        new MainService().run(args);
    }

    @Override
    public void initialize(final Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.setName("main");
        bootstrap.addBundle(new MigrationsBundle<MainConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(final MainConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        });
    }

    @Override
    public void run(final MainConfiguration config, final Environment env) throws Exception {

        final DBI jdbi = createDBI(config, env);
        final SystemUserDAO systemUserDAO = jdbi.onDemand(SystemUserDAO.class);

        env.addFilter(UTF8EncodingFilter.class, "*");

        env.addFilter(CrossOriginFilter.class, "*")
                .setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, config.getCORSConfiguration().getAllowedOrigins())
                .setInitParam(CrossOriginFilter.ALLOWED_HEADERS_PARAM, config.getCORSConfiguration().getAllowedHeaders())
                .setInitParam(CrossOriginFilter.ALLOWED_METHODS_PARAM, config.getCORSConfiguration().getAllowedMethods())
                .setInitParam(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, config.getCORSConfiguration().getAllowedCredentials());

        env.addProvider(new OAuthProvider<>(
                CachingAuthenticator.wrap(
                        new SystemUserAuthenticator(systemUserDAO, config.getSecurityConfiguration().getDefaultTokenTimeout()),
                        config.getSecurityConfiguration().getAuthenticationCachePolicy()), "systemuser"));

        env.addResource(new RegistrationResource(systemUserDAO, config.getSecurityConfiguration().getDefaultTokenTimeout()));
        env.addResource(new AuthenticationResource(systemUserDAO, config.getSecurityConfiguration().getDefaultTokenTimeout()));
        env.addResource(new SystemUserResource());

    }

    private DBI createDBI(final MainConfiguration config, final Environment environment) throws ClassNotFoundException {
        final DBI jdbi = new DBIFactory().build(environment, config.getDatabaseConfiguration(), "postgresql");
        jdbi.registerArgumentFactory(new DateAsTimestampArgument());
        return jdbi;
    }

}
