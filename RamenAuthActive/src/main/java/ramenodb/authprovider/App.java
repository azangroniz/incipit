package ramenodb.authprovider;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.ws.rs.client.Client;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import ramenodb.authprovider.RamenodbConfiguration.ClientSecretsConfiguration;
import ramenodb.authprovider.auth.SimpleAuthenticator;
import ramenodb.authprovider.core.User;
import ramenodb.authprovider.db.UserDAO;
import ramenodb.authprovider.resources.AuthResource;
import ramenodb.authprovider.resources.UserResource;

/**
 *
 * @author jmfabiano
 */
public class App extends Application<RamenodbConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    private final HibernateBundle<RamenodbConfiguration> hibernateBundle
            = new HibernateBundle<RamenodbConfiguration>(User.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(final RamenodbConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "Ramen";
    }

    @Override
    public void initialize(final Bootstrap<RamenodbConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<RamenodbConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(final RamenodbConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(final RamenodbConfiguration configuration, final Environment environment)
            throws ClassNotFoundException {

        final UserDAO dao = new UserDAO(hibernateBundle.getSessionFactory());
        final Client client
                = new JerseyClientBuilder(environment).using(configuration.getJerseyClient())
                .build(getName());
        final ClientSecretsConfiguration clientSecrets = configuration.getClientSecrets();

        environment.jersey().register(new UserResource(dao));
        environment.jersey().register(new AuthResource(client, dao, clientSecrets));

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, environment.getApplicationContext().getContextPath() + "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization, roles");
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        environment.jersey().register(AuthFactory.binder(new OAuthFactory<User>(new SimpleAuthenticator(),
                "bearer",
                User.class)));
    }
}
