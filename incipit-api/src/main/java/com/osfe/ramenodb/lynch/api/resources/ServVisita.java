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

import com.osfe.ramenodb.lynch.api.db.RepoVisita;
import com.osfe.ramenodb.lynch.api.model.Visita;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

/**
*
* @author jmfabiano
*/
@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServVisita {

	private final RepoVisita dao;

	public ServVisita(RepoVisita dao) {
		this.dao = dao;
	}

	@GET
	@UnitOfWork
	@Path("/visita/{id}")
	public Visita findById(@PathParam("id") LongParam id) {
		return dao.getById(id.get());
	}

	@GET
	@UnitOfWork
	@Path("/visitas")
	public List<Visita> getAllNotPaged() {
		return dao.get();
	}

	@POST
	@UnitOfWork
	@Path("/visitas/porids")
	public List<Visita> findById(List<Serializable> ids) {
		return dao.getByIds(ids);
	}

	@PUT
	@UnitOfWork
	@Path("/visita")
	public void create(Visita entity) {
		dao.persist(entity);
	}

	@POST
	@UnitOfWork
	@Path("/visita")
	public Visita merge(Visita entity) {
		return dao.merge(entity);
	}

	@DELETE
	@UnitOfWork
	@Path("/visita/{id}")
	public void remove(@PathParam("id") LongParam id) {
		dao.deleteById(id.get());
	}
}
