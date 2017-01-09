/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.auxi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jmfabiano
 */
@Entity
@Table(name = "nomenclador")
public class Nomenclador implements Serializable {

	private static final long serialVersionUID = 3034692444773786053L;

	@Id
	private Long id;
	@Column(name = "codigo")
	private int codigo;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "tipo_nomenclador")
	private String tipoNomenclador;

	public Nomenclador() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoNomenclador() {
		return tipoNomenclador;
	}

	public void setTipoNomenclador(String tipoNomenclador) {
		this.tipoNomenclador = tipoNomenclador;
	}

}
