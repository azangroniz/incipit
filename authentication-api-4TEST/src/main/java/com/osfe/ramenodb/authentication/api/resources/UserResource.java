package com.osfe.ramenodb.authentication.api.resources;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import com.osfe.ramenodb.authentication.api.core.User;
import com.osfe.ramenodb.authentication.api.db.RepoUser;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.errors.ErrorMessage;

@Path("/api/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private final RepoUser dao;

	public UserResource(RepoUser userDAO) {
		this.dao = userDAO;
	}

	@GET
	@UnitOfWork
	public Response getUser(@Auth User user, @Context HttpServletRequest request) throws ParseException, JOSEException {
		Optional<User> foundUser = getAuthUser(user);

		if (!foundUser.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(foundUser.get()).build();
	}

	// for testing
	@GET
	@Path("/all")
	@UnitOfWork
	public Response getAllUsers() {
		return Response.ok().entity(dao.findAll()).build();
	}

	@PUT
	@UnitOfWork
	public Response updateUser(@Valid User user) throws ParseException, JOSEException {
		Optional<User> foundUser = getAuthUser(user);

		if (!foundUser.isPresent()) {
			return Response.status(Status.NOT_FOUND).entity(new ErrorMessage(AuthResource.NOT_FOUND_MSG)).build();
		}

		User userToUpdate = foundUser.get();
		userToUpdate.setDisplayName(user.getDisplayName());
		userToUpdate.setUniqueName(user.getUniqueName());
		userToUpdate.setEmail(user.getEmail());
		dao.merge(userToUpdate);

		return Response.ok().build();
	}

	/*
	 * Helper methods
	 */
	private Optional<User> getAuthUser(User user) throws ParseException, JOSEException {
		Optional<User> dBUser = dao.findByUniqueName();
		user.setEmail(dBUser.get().getEmail());
		user.setProviderId(User.Provider.FACEBOOK, dBUser.get().getFacebook());
		return Optional.of(user);
	}

}
