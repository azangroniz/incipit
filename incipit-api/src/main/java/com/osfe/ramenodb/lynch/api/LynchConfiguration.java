package com.osfe.ramenodb.lynch.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;

/**
 *
 * @author jmfabiano
 */
public class LynchConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty("databaseMySQL")
	private DataSourceFactory databaseMySQL = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		return databaseMySQL;
	}

	@Valid
	@NotNull
	@JsonProperty("databaseAuth")
	private DataSourceFactory databaseAuth = new DataSourceFactory();

	public DataSourceFactory getAuthDataSourceFactory() {
		return databaseAuth;
	}

	@Valid
	@NotNull
	@JsonProperty("databaseSQLServer")
	private DataSourceFactory databaseSQLServer = new DataSourceFactory();

	public DataSourceFactory getSQLServerDataSourceFactory() {
		return databaseSQLServer;
	}

	@Valid
	@NotNull
	private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

	@JsonProperty("jerseyClient")
	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jerseyClient;
	}
}
