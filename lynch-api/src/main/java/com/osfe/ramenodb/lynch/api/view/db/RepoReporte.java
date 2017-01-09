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
import com.osfe.ramenodb.lynch.api.view.model.Reporte;

/**
 *
 * @author jmfabiano
 */
public class RepoReporte extends GenericRepo<Reporte, Serializable> {

	public RepoReporte(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Reporte> getReporte() {
		@SuppressWarnings("unchecked")
		List<Reporte> reporte = currentSession()
				.createSQLQuery(" SELECT v.id," + " v.fecha," + " v.afiliado_Nombre     afiliadoNombre,"
						+ " v.afiliado_Apellido   afiliadoApellido," + " v.afiliado_dni        afiliadoDni,"
						+ " v.afiliado_numero     afiliadoNumero," + " esp.descripcion       descripcionEspecialidad,"
						+ " li.descripcion 	      descripcionLinea,"
						+ " pro.nombre_completo       descripcionProfesional," + " v.estado, "
						+ " cie.descripcion       descripcionCIE10," + " dsmiv.descripcion     descripcionDSMIV,"
						+ " nom.descripcion       descripcionNomenclador," + " v.observacion" + " FROM lynch.visita v"
						+ " inner join lynch.practica 			p on p.id_visita=v.id"
						+ " left join generalmysql.cie10        cie   on   cie.id=p.id_Cie10"
						+ " left join generalmysql.dsmiv        dsmiv on   dsmiv.id=p.id_dsmiv"
						+ " inner join generalmysql.nomenclador  nom   on   nom.id=p.id_nomenclador"
						+ " inner join generalmysql.linea        li    on   li.id=v.id_linea"
						+ " inner join lynch.especialidad        esp   on   esp.id=v.id_especialidad"
						+ " inner join lynch.profesional         pro   on   pro.id=v.id_profesional ")
				.setResultSetMapping("reporteMApping").list();
		return reporte;
	}

	public List<Reporte> getByProf(Long id) {
		@SuppressWarnings("unchecked")
		List<Reporte> reporte = currentSession().createSQLQuery(" SELECT v.id," + " v.fecha,"
				+ " v.afiliado_Nombre     afiliadoNombre," + " v.afiliado_Apellido   afiliadoApellido,"
				+ " v.afiliado_dni        afiliadoDni," + " v.afiliado_numero     afiliadoNumero,"
				+ " esp.descripcion       descripcionEspecialidad," + " li.descripcion 	      descripcionLinea,"
				+ " pro.nombre_completo       descripcionProfesional," + " v.estado, "
				+ " cie.descripcion       descripcionCIE10," + " dsmiv.descripcion     descripcionDSMIV,"
				+ " nom.descripcion       descripcionNomenclador," + " v.observacion" + " FROM lynch.visita v"
				+ " inner join lynch.practica 			p on p.id_visita=v.id"
				+ " left join generalmysql.cie10        cie   on   cie.id=p.id_Cie10"
				+ " left join generalmysql.dsmiv        dsmiv on   dsmiv.id=p.id_dsmiv"
				+ " inner join generalmysql.nomenclador  nom   on   nom.id=p.id_nomenclador"
				+ " inner join generalmysql.linea        li    on   li.id=v.id_linea"
				+ " inner join lynch.especialidad        esp   on   esp.id=v.id_especialidad"
				+ " inner join lynch.profesional         pro   on   pro.id=v.id_profesional "
				+ " WHERE v.id_profesional = " + id).setResultSetMapping("reporteMApping").list();
		return reporte;
	}

	public List<Reporte> getByEsp(Long id) {
		@SuppressWarnings("unchecked")
		List<Reporte> reporte = currentSession().createSQLQuery(" SELECT v.id," + " v.fecha,"
				+ " v.afiliado_Nombre     afiliadoNombre," + " v.afiliado_Apellido   afiliadoApellido,"
				+ " v.afiliado_dni        afiliadoDni," + " v.afiliado_numero     afiliadoNumero,"
				+ " esp.descripcion       descripcionEspecialidad," + " li.descripcion 	      descripcionLinea,"
				+ " pro.nombre_completo       descripcionProfesional," + " v.estado, "
				+ " cie.descripcion       descripcionCIE10," + " dsmiv.descripcion     descripcionDSMIV,"
				+ " nom.descripcion       descripcionNomenclador," + " v.observacion" + " FROM lynch.visita v"
				+ " inner join lynch.practica 			p on p.id_visita=v.id"
				+ " left join generalmysql.cie10        cie   on   cie.id=p.id_Cie10"
				+ " left join generalmysql.dsmiv        dsmiv on   dsmiv.id=p.id_dsmiv"
				+ " inner join generalmysql.nomenclador  nom   on   nom.id=p.id_nomenclador"
				+ " inner join generalmysql.linea        li    on   li.id=v.id_linea"
				+ " inner join lynch.especialidad        esp   on   esp.id=v.id_especialidad"
				+ " inner join lynch.profesional         pro   on   pro.id=v.id_profesional "
				+ " WHERE v.id_especialidad = " + id).setResultSetMapping("reporteMApping").list();
		return reporte;
	}

	public List<Reporte> getByDNI(Long id) {
		@SuppressWarnings("unchecked")
		List<Reporte> reporte = currentSession().createSQLQuery(" SELECT v.id," + " v.fecha,"
				+ " v.afiliado_Nombre     afiliadoNombre," + " v.afiliado_Apellido   afiliadoApellido,"
				+ " v.afiliado_dni        afiliadoDni," + " v.afiliado_numero     afiliadoNumero,"
				+ " esp.descripcion       descripcionEspecialidad," + " li.descripcion 	      descripcionLinea,"
				+ " pro.nombre_completo       descripcionProfesional," + " v.estado, "
				+ " cie.descripcion       descripcionCIE10," + " dsmiv.descripcion     descripcionDSMIV,"
				+ " nom.descripcion       descripcionNomenclador," + " v.observacion" + " FROM lynch.visita v"
				+ " inner join lynch.practica 			p on p.id_visita=v.id"
				+ " left join generalmysql.cie10        cie   on   cie.id=p.id_Cie10"
				+ " left join generalmysql.dsmiv        dsmiv on   dsmiv.id=p.id_dsmiv"
				+ " inner join generalmysql.nomenclador  nom   on   nom.id=p.id_nomenclador"
				+ " inner join generalmysql.linea        li    on   li.id=v.id_linea"
				+ " inner join lynch.especialidad        esp   on   esp.id=v.id_especialidad"
				+ " inner join lynch.profesional         pro   on   pro.id=v.id_profesional "
				+ " WHERE v.afiliado_dni = " + id).setResultSetMapping("reporteMApping").list();
		return reporte;
	}

	public List<Reporte> getByFecha(String desde, String hasta) {
		@SuppressWarnings("unchecked")
		List<Reporte> reporte = currentSession()
				.createSQLQuery(" SELECT v.id," + " v.fecha," + " v.afiliado_Nombre     afiliadoNombre,"
						+ " v.afiliado_Apellido   afiliadoApellido," + " v.afiliado_dni        afiliadoDni,"
						+ " v.afiliado_numero     afiliadoNumero," + " esp.descripcion       descripcionEspecialidad,"
						+ " li.descripcion 	      descripcionLinea,"
						+ " pro.nombre_completo       descripcionProfesional," + " v.estado, "
						+ " cie.descripcion       descripcionCIE10," + " dsmiv.descripcion     descripcionDSMIV,"
						+ " nom.descripcion       descripcionNomenclador," + " v.observacion" + " FROM lynch.visita v"
						+ " inner join lynch.practica 			p on p.id_visita=v.id"
						+ " left join generalmysql.cie10        cie   on   cie.id=p.id_Cie10"
						+ " left join generalmysql.dsmiv        dsmiv on   dsmiv.id=p.id_dsmiv"
						+ " inner join generalmysql.nomenclador  nom   on   nom.id=p.id_nomenclador"
						+ " inner join generalmysql.linea        li    on   li.id=v.id_linea"
						+ " inner join lynch.especialidad        esp   on   esp.id=v.id_especialidad"
						+ " inner join lynch.profesional         pro   on   pro.id=v.id_profesional "
						+ " WHERE fecha BETWEEN  \'" + desde + "\' AND \'" + hasta + "\'")
				.setResultSetMapping("reporteMApping").list();
		return reporte;
	}
}
