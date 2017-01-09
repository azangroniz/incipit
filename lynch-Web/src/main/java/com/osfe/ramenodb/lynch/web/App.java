package com.osfe.ramenodb.lynch.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.joda.time.DateTimeZone;

import com.osfe.ramenodb.lynch.web.resources.ClientResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 * @author jmfabiano
 */

public class App extends Application<RamenodbConfiguration> {

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}

	@Override
	public String getName() {
		return "RamenWeb";
	}

	@Override
	public void initialize(final Bootstrap<RamenodbConfiguration> bootstrap) {
		DateTimeZone.setDefault(DateTimeZone.UTC);
		bootstrap.addBundle(new AssetsBundle());
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
