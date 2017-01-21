/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.view.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.osfe.ramenodb.lynch.api.view.db.RepoPendiente;
import com.osfe.ramenodb.lynch.api.view.model.Pendiente;

import io.dropwizard.hibernate.UnitOfWork;

/**
 *
 * @author jmfabiano
 */
@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VistaPendiente {
	private final RepoPendiente repoCarga;

	public VistaPendiente(RepoPendiente repoCarga) {
		this.repoCarga = repoCarga;
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/vista/pendientes")
	public List<Pendiente> getPendientes() {
		return repoCarga.getPendientes();
	}

}
