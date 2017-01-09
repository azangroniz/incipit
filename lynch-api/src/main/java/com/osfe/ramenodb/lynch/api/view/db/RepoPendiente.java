/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.view.db;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import com.osfe.ramenodb.lynch.api.db.GenericRepo;
import com.osfe.ramenodb.lynch.api.view.model.Pendiente;

/**
 *
 * @author jmfabiano
 */
public class RepoPendiente extends GenericRepo<Pendiente, Serializable> {

	public RepoPendiente(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Pendiente> getPendientes() {
		@SuppressWarnings("unchecked")
		List<Pendiente> cargas = currentSession()
				.createSQLQuery("select " + "v.id id, " + "v.afiliado_apellido afiliadoApellido, "
						+ "v.afiliado_nombre afiliadoNombre, " + "v.afiliado_numero afiliadoNumero, "
						+ "e.descripcion especialidad, " + "p.nombre_completo nombreProfesional "
						+ "from lynch.visita v " + "inner join especialidad e on e.id=v.id_especialidad "
						+ "inner join profesional p on p.id=v.id_profesional " + "where v.estado= " + 0)
				.setResultSetMapping("cargaMapping").list();
		return cargas;
	}

}
