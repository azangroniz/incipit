/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.view.resource;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jmfabiano
 */
@Path("/v1")
public class TestingExports {

	@GET
	@Path("/vista/export")
	public Response getFile() {
		File file = new File("lemon.jpg");
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") // optional
				.build();
	}

}
