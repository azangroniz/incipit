/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.view.resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osfe.ramenodb.lynch.api.view.db.RepoReporte;
import com.osfe.ramenodb.lynch.api.view.model.Reporte;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author jmfabiano
 */
@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServReporte {

	private final RepoReporte repoReporte;
	private static final String templateFileName = "src/main/resources/templates/XAfiliados.xls";
	private static final String destFileName = "src/main/resources/templates/afiliados_output.xls";

	public ServReporte(RepoReporte repoReporte) {
		this.repoReporte = repoReporte;
	}

	@POST
	@Path("/reporte/exportar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response printReport(String listaReportes)
			throws ParsePropertyException, IOException, InvalidFormatException {
		List<Reporte> reportes = new ObjectMapper().readValue(listaReportes, new TypeReference<List<Reporte>>() {
		});

		Map<String, List<Reporte>> beans = new HashMap<String, List<Reporte>>();
		beans.put("reporte", reportes);
		XLSTransformer transformer = new XLSTransformer();
		transformer.markAsFixedSizeCollection("reportes");
		transformer.transformXLS(templateFileName, beans, destFileName);

		File file = new File(destFileName);
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + "excel.xls" + "\"") // optional
				.build();

	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/reporte/profesional/{id}")
	public List<Reporte> getByProf(@PathParam("id") LongParam id) {
		return repoReporte.getByProf(id.get());
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/reporte/especialidad/{id}")
	public List<Reporte> getByEsp(@PathParam("id") LongParam id) {
		return repoReporte.getByEsp(id.get());
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/reporte/dni/{id}")
	public List<Reporte> getByDNI(@PathParam("id") LongParam id) {
		return repoReporte.getByDNI(id.get());
	}

	@GET
	@Timed
	@UnitOfWork
	@Path("/reporte/fecha/{desde}/{hasta}")
	public List<Reporte> getByFacha(@PathParam("desde") String desde, @PathParam("hasta") String hasta) {
		return repoReporte.getByFecha(desde, hasta);
	}

}
