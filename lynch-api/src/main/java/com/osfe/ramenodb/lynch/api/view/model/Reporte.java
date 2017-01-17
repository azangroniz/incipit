/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.view.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmfabiano
 */
@Entity
@Table(name = "XXX_repo")
@XmlRootElement
@SqlResultSetMapping(name = "reporteMApping", entities = { @EntityResult(entityClass = Reporte.class, fields = {
		@FieldResult(name = "id", column = "id"), @FieldResult(name = "afiliadoNombre", column = "afiliadoNombre"),
		@FieldResult(name = "afiliadoApellido", column = "afiliadoApellido"),
		@FieldResult(name = "fecha", column = "fecha"), @FieldResult(name = "afiliadoDni", column = "afiliadoDni"),
		@FieldResult(name = "afiliadoNumero", column = "afiliadoNumero"),
		@FieldResult(name = "descripcionLinea", column = "descripcionLinea"),
		@FieldResult(name = "descripcionProfesional", column = "descripcionProfesional"),
		@FieldResult(name = "descripcionEspecialidad", column = "descripcionEspecialidad"),
		@FieldResult(name = "descripcionCIE10", column = "descripcionCIE10"),
		@FieldResult(name = "descripcionDSMIV", column = "descripcionDSMIV"),
		@FieldResult(name = "descripcionNomenclador", column = "descripcionNomenclador"),
		@FieldResult(name = "observacion", column = "observacion") }) })
public class Reporte implements Serializable {

	private static final long serialVersionUID = -457893185489914583L;

	@Id
	private Long id;

	private String afiliadoNombre;

	private String afiliadoApellido;

	@Temporal(javax.persistence.TemporalType.DATE)
	private Date fecha;

	private Long afiliadoDni;

	private String afiliadoNumero;

	private String descripcionLinea;

	private String descripcionEspecialidad;

	private String descripcionProfesional;

	private String descripcionCIE10;

	private String descripcionDSMIV;

	private String descripcionNomenclador;

	public Reporte() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAfiliadoNombre() {
		return afiliadoNombre;
	}

	public void setAfiliadoNombre(String afiliadoNombre) {
		this.afiliadoNombre = afiliadoNombre;
	}

	public String getAfiliadoApellido() {
		return afiliadoApellido;
	}

	public void setAfiliadoApellido(String afiliadoApellido) {
		this.afiliadoApellido = afiliadoApellido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getAfiliadoDni() {
		return afiliadoDni;
	}

	public void setAfiliadoDni(Long afiliadoDni) {
		this.afiliadoDni = afiliadoDni;
	}

	public String getAfiliadoNumero() {
		return afiliadoNumero;
	}

	public void setAfiliadoNumero(String afiliadoNumero) {
		this.afiliadoNumero = afiliadoNumero;
	}

	public String getDescripcionLinea() {
		return descripcionLinea;
	}

	public void setDescripcionLinea(String descripcionLinea) {
		this.descripcionLinea = descripcionLinea;
	}

	public String getDescripcionEspecialidad() {
		return descripcionEspecialidad;
	}

	public void setDescripcionEspecialidad(String descripcionEspecialidad) {
		this.descripcionEspecialidad = descripcionEspecialidad;
	}

	public String getDescripcionProfesional() {
		return descripcionProfesional;
	}

	public void setDescripcionProfesional(String descripcionProfesional) {
		this.descripcionProfesional = descripcionProfesional;
	}

	public String getDescripcionCIE10() {
		return descripcionCIE10;
	}

	public void setDescripcionCIE10(String descripcionCIE10) {
		this.descripcionCIE10 = descripcionCIE10;
	}

	public String getDescripcionDSMIV() {
		return descripcionDSMIV;
	}

	public void setDescripcionDSMIV(String descripcionDSMIV) {
		this.descripcionDSMIV = descripcionDSMIV;
	}

	public String getDescripcionNomenclador() {
		return descripcionNomenclador;
	}

	public void setDescripcionNomenclador(String descripcionNomenclador) {
		this.descripcionNomenclador = descripcionNomenclador;
	}

}
