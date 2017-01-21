/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jmfabiano
 */
@Entity
@Table(name = "practica")
public class Practica implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@JoinColumn(name = "id_visita")
	@ManyToOne(optional = false)
	private Visita idVisita;
	@Basic(optional = false)
	@Column(name = "id_nomenclador")
	private Long idNomenclador;
	@Basic(optional = false)
	@Column(name = "id_cie10")
	private Long idCIE10;
	@Basic(optional = false)
	@Column(name = "id_dsmiv")
	private Long idDSMIV;

	public Practica() {
	}

	public Practica(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Visita getIdVisita() {
		return idVisita;
	}

	public void setIdVisita(Visita idVisita) {
		this.idVisita = idVisita;
	}

	public Long getIdNomenclador() {
		return idNomenclador;
	}

	public void setIdNomenclador(Long idNomenclador) {
		this.idNomenclador = idNomenclador;
	}

	public Long getIdCIE10() {
		return idCIE10;
	}

	public void setIdCIE10(Long idCIE10) {
		this.idCIE10 = idCIE10;
	}

	public Long getIdDSMIV() {
		return idDSMIV;
	}

	public void setIdDSMIV(Long idDSMIV) {
		this.idDSMIV = idDSMIV;
	}

	@Override
	public String toString() {
		return "e.Practica[ id=" + id + " ]";
	}

}
