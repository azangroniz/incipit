/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmfabiano
 */
@Entity
@Table(name = "visita")
@XmlRootElement
public class Visita implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "afiliado_nombre")
	private String afiliadoNombre;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "afiliado_apellido")
	private String afiliadoApellido;
	@Basic(optional = false)
	@NotNull
	@Column(name = "afiliado_dni")
	private Long afiliadoDni;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "afiliado_numero")
	private String afiliadoNumero;
	@Column(name = "id_linea")
	private Long idLinea;
	@JoinColumn(name = "id_profesional", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Profesional idProfesional;
	@JoinColumn(name = "id_especialidad", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Especialidad idEspecialidad;
	@Basic(optional = false)
	@NotNull
	@Column(name = "estado")
	private int estado;
	@Basic(optional = true)
	@Size(max = 250)
	@Column(name = "observacion")
	private String observacion;

	public Visita() {
	}

	public Visita(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getAfiliadoNombre() {
		return afiliadoNombre;
	}

	public void setAfiliadoNombre(String afiliadoNombre) {
		this.afiliadoNombre = afiliadoNombre.trim();
	}

	public String getAfiliadoApellido() {
		return afiliadoApellido;
	}

	public void setAfiliadoApellido(String afiliadoApellido) {
		this.afiliadoApellido = afiliadoApellido.trim();
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Profesional getIdProfesional() {
		return idProfesional;
	}

	public void setIdProfesional(Profesional idProfesional) {
		this.idProfesional = idProfesional;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Especialidad getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(Especialidad idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

	@Override
	public String toString() {
		return "e.Visita[ id=" + id + " ]";
	}

}
