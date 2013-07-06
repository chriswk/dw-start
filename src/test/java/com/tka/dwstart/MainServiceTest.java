package com.tka.dwstart;

import com.tka.dwstart.filters.UTF8EncodingFilter;
import com.tka.dwstart.resources.LoginResource;
import com.tka.dwstart.resources.RegisterResource;
import com.tka.dwstart.resources.SystemUserResource;
import com.yammer.dropwizard.auth.oauth.OAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class MainServiceTest {

    private final Environment env = mock(Environment.class);
    private final MainConfiguration configuration = new MainConfiguration();
    private final DatabaseConfiguration dbconfig = new DatabaseConfiguration();
    private final Bootstrap<MainConfiguration> bootstrap = mock(Bootstrap.class);

    @Test
    public void should_bootstrap_service() {
        final MainService main = new MainService();
        main.initialize(bootstrap);
        verify(bootstrap).setName("main");
        verify(bootstrap).addBundle(any(MigrationsBundle.class));
    }

    @Test @Ignore
    public void should_start_main_service() throws Exception {
        final MainConfiguration configuration = new MainConfiguration();
        final MainService main = new MainService();

        main.run(configuration, env);

        verify(env).addFilter(UTF8EncodingFilter.class, "*");
        verify(env).addProvider(any(OAuthProvider.class));
        verify(env).addResource(any(LoginResource.class));
        verify(env).addResource(any(RegisterResource.class));
        verify(env).addResource(any(SystemUserResource.class));

    }

}
