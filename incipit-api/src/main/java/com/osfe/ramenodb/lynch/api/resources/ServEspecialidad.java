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
import com.osfe.ramenodb.lynch.api.db.RepoEspecialidad;
import com.osfe.ramenodb.lynch.api.model.Especialidad;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

/**
*
* @author jmfabiano
*/
@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServEspecialidad {

	private final RepoEspecialidad dao;

	public ServEspecialidad(RepoEspecialidad dao) {
		this.dao = dao;
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/especialidad/{id}")
	public Especialidad findById(@PathParam("id") LongParam id) {
		return dao.getById(id.get());
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/especialidades")
	public List<Especialidad> getAllNotPaged() {
		return dao.get();
	}

	@POST
	@Timed
	@UnitOfWork
	@Path("/especialidades/porids")
	public List<Especialidad> findById(List<Serializable> ids) {
		return dao.getByIds(ids);
	}

	@PUT
	@Timed
	@UnitOfWork
	@Path("/especialidad")
	public void create(Especialidad entity) {
		dao.persist(entity);
	}

	@POST
	@Timed
	@UnitOfWork
	@Path("/especialidad")
	public Especialidad merge(Especialidad entity) {
		return dao.merge(entity);
	}

	@DELETE
	@Timed
	@UnitOfWork
	@Path("/especialidad/{id}")
	public void remove(@PathParam("id") LongParam id) {
		dao.deleteById(id.get());
	}
}