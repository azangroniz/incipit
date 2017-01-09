/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.resources;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.osfe.ramenodb.lynch.api.db.RepoPractica;
import com.osfe.ramenodb.lynch.api.model.Practica;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

/**
*
* @author jmfabiano
*/
@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServPractica {

	private final RepoPractica dao;

	public ServPractica(RepoPractica dao) {
		this.dao = dao;
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/practica/{id}")
	public Practica findById(@PathParam("id") LongParam id) {
		return dao.getById(id.get());
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/practicas")
	public List<Practica> getAllNotPaged() {
		return dao.get();
	}

	@POST
	@Timed
	@UnitOfWork
	@Path("/practicas/porids")
	public List<Practica> findById(List<Serializable> ids) {
		return dao.getByIds(ids);
	}

	@PUT
	@Timed
	@UnitOfWork
	@Path("/practica")
	public void create(Practica entity) {
		dao.persist(entity);
	}

	@PUT
	@Timed
	@UnitOfWork
	@Path("/practicas")
	public void create(List<Practica> entities) {
		for (Practica entity : entities) {
			dao.persist(entity);
		}
	}

	@POST
	@Timed
	@UnitOfWork
	@Path("/practica")
	public Practica merge(Practica entity) {
		return dao.merge(entity);
	}

	@DELETE
	@Timed
	@UnitOfWork
	@Path("/practica/{id}")
	public void remove(@PathParam("id") LongParam id) {
		dao.deleteById(id.get());
	}
}
