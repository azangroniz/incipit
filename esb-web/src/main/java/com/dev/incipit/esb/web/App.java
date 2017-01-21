package com.dev.incipit.esb.web;

import com.dev.incipit.esb.web.resources.ClientResource;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.joda.time.DateTimeZone;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * @author jmfabiano
 */

public class App extends Application<RamenodbConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "esb-Web";
    }

    @Override
    public void initialize(final Bootstrap<RamenodbConfiguration> bootstrap) {
        DateTimeZone.setDefault(DateTimeZone.UTC);
        bootstrap.addBundle(new ConfiguredAssetsBundle());
    }

    @Override
    public void run(final RamenodbConfiguration configuration, final Environment environment)
            throws ClassNotFoundException {

        environment.jersey().register(new ClientResource());

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true,
                environment.getApplicationContext().getContextPath() + "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "Origin, Content-Type, Accept, Authorization, roles");
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

    }
}
