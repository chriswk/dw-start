package com.tka.dwstart;

import com.tka.dwstart.auth.SystemUserAuthenticator;
import com.tka.dwstart.filters.UTF8EncodingFilter;
import com.tka.dwstart.jdbi.DateAsTimestampArgument;
import com.tka.dwstart.jdbi.SystemUserDAO;
import com.tka.dwstart.resources.LoginResource;
import com.tka.dwstart.resources.RegisterResource;
import com.tka.dwstart.resources.SystemUserResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.CachingAuthenticator;
import com.yammer.dropwizard.auth.oauth.OAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import org.skife.jdbi.v2.DBI;

public final class MainService extends Service<MainConfiguration> {

    public static void main(final String[] args) throws Exception {
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

        env.addProvider(new OAuthProvider<>(
                CachingAuthenticator.wrap(new SystemUserAuthenticator(systemUserDAO, config.getDefaultTokenTimeout()),
                    config.getAuthenticationCachePolicy()), "systemuser"));

        env.addResource(new RegisterResource(systemUserDAO, config.getDefaultTokenTimeout()));
        env.addResource(new LoginResource(systemUserDAO, config.getDefaultTokenTimeout()));
        env.addResource(new SystemUserResource());

    }

    private DBI createDBI(final MainConfiguration config, final Environment environment) throws ClassNotFoundException {
        final DBI jdbi = new DBIFactory().build(environment, config.getDatabaseConfiguration(), "postgresql");
        jdbi.registerArgumentFactory(new DateAsTimestampArgument());
        return jdbi;
    }

}
