package com.osfe.ramenodb.lynch.api;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.joda.time.DateTimeZone;

import com.osfe.ramenodb.lynch.api.auth.SimpleAuthenticator;
import com.osfe.ramenodb.lynch.api.core.User;
import com.osfe.ramenodb.lynch.api.db.RepoEspecialidad;
import com.osfe.ramenodb.lynch.api.db.RepoPractica;
import com.osfe.ramenodb.lynch.api.db.RepoProfesional;
import com.osfe.ramenodb.lynch.api.db.RepoVisita;
import com.osfe.ramenodb.lynch.api.model.Especialidad;
import com.osfe.ramenodb.lynch.api.model.Practica;
import com.osfe.ramenodb.lynch.api.model.Profesional;
import com.osfe.ramenodb.lynch.api.model.Visita;
import com.osfe.ramenodb.lynch.api.resources.ServEspecialidad;
import com.osfe.ramenodb.lynch.api.resources.ServPractica;
import com.osfe.ramenodb.lynch.api.resources.ServProfesionales;
import com.osfe.ramenodb.lynch.api.resources.ServVisita;
import com.osfe.ramenodb.lynch.api.view.db.RepoPendiente;
import com.osfe.ramenodb.lynch.api.view.db.RepoReporte;
import com.osfe.ramenodb.lynch.api.view.model.Pendiente;
import com.osfe.ramenodb.lynch.api.view.model.Reporte;
import com.osfe.ramenodb.lynch.api.view.resource.ServReporte;
import com.osfe.ramenodb.lynch.api.view.resource.TestingExports;
import com.osfe.ramenodb.lynch.api.view.resource.VistaPendiente;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 * @author jmfabiano
 */
public class LynchApplication extends Application<LynchConfiguration> {

	private final HibernateBundle<LynchConfiguration> hibernateMySQL = new HibernateBundle<LynchConfiguration>(
			Reporte.class, Visita.class, Especialidad.class, Profesional.class, Pendiente.class, Practica.class) {

		@Override
		public DataSourceFactory getDataSourceFactory(LynchConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}

		@Override
		protected String name() {
			return "hibernate";
		}
	};
	private final HibernateBundle<LynchConfiguration> hibernateAuth = new HibernateBundle<LynchConfiguration>(
			User.class) {

		@Override
		public DataSourceFactory getDataSourceFactory(LynchConfiguration configuration) {
			return configuration.getAuthDataSourceFactory();
		}

		@Override
		protected String name() {
			return "hibernate.just4Auth";
		}
	};

	public static void main(String[] args) throws Exception {
		new LynchApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<LynchConfiguration> bootstrap) {
		DateTimeZone.setDefault(DateTimeZone.UTC);
		bootstrap.addBundle(hibernateMySQL);
		bootstrap.addBundle(hibernateAuth);
	}

	@Override
	public void run(LynchConfiguration configuration, Environment environment) throws ClassNotFoundException {

		final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
				.build(getName());

		final RepoVisita repoVisita = new RepoVisita(hibernateMySQL.getSessionFactory());
		final RepoProfesional repoProfesional = new RepoProfesional(hibernateMySQL.getSessionFactory());
		final RepoEspecialidad repoEspecialidad = new RepoEspecialidad(hibernateMySQL.getSessionFactory());
		final RepoPendiente repocarga = new RepoPendiente(hibernateMySQL.getSessionFactory());
		final RepoReporte repoReporte = new RepoReporte(hibernateMySQL.getSessionFactory());
		final RepoPractica repoPractica = new RepoPractica((hibernateMySQL.getSessionFactory()));

		environment.jersey().register(new ServVisita(repoVisita));
		environment.jersey().register(new ServProfesionales(repoProfesional));
		environment.jersey().register(new ServEspecialidad(repoEspecialidad));
		environment.jersey().register(new VistaPendiente(repocarga));
		environment.jersey().register(new TestingExports());
		environment.jersey().register(new ServReporte(repoReporte));
		environment.jersey().register(new ServPractica(repoPractica));

		environment.jersey()
				.register(AuthFactory.binder(new OAuthFactory<User>(new SimpleAuthenticator(), "bearer", User.class)));

		FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true,
				environment.getApplicationContext().getContextPath() + "*");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
		filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

	}
}
