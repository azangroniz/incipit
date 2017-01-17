package com.osfe.ramenodb.authentication.api.resources;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import com.osfe.ramenodb.authentication.api.auth.AuthUtils;
import com.osfe.ramenodb.authentication.api.core.User;
import com.osfe.ramenodb.authentication.api.db.RepoUser;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.ParseException;

@Path("/api/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final RepoUser dao;

    public UserResource(RepoUser dao) {
        this.dao = dao;
    }

    @GET
    @UnitOfWork
    public Response getUser(@Context HttpServletRequest request) throws ParseException, JOSEException {
        Optional<User> foundUser = getAuthUser(request);

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
    public Response updateUser(@Valid User user, @Context HttpServletRequest request) throws ParseException, JOSEException {
        Optional<User> foundUser = getAuthUser(request);

        if (!foundUser.isPresent()) {
            return Response
                    .status(Status.NOT_FOUND)
                    .entity(new ErrorMessage(AuthResource.NOT_FOUND_MSG)).build();
        }

        User userToUpdate = foundUser.get();
        userToUpdate.setDisplayName(user.getDisplayName());
        userToUpdate.setEmail(user.getEmail());
        dao.save(userToUpdate);

        return Response.ok().build();
    }

    /*
     * Helper methods
     */
    private Optional<User> getAuthUser(HttpServletRequest request) throws ParseException, JOSEException {
        String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        return dao.findById(Long.parseLong(subject));
    }

}
