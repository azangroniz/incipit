/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmfabiano
 */
@Entity
@Table(name = "profesional")
@XmlRootElement
public class Profesional implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "nombre_completo")
	private String nombreCompleto;
	@Column(name = "matricula")
	private Integer matricula;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_especialidad", referencedColumnName = "id")
	private Especialidad idEspecialidad;
	@Basic(optional = false)
	@NotNull
	@Column(name = "vigente")
	private boolean vigente;
	// @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesionales")
	// private Collection<Visita> visitaCollection;

	public Profesional() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Especialidad getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(Especialidad idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public boolean getVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	@Override
	public String toString() {
		return "e.Profesional[ id=" + id + " ]";
	}

}
