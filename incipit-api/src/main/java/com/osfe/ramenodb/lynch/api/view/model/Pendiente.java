package com.osfe.ramenodb.lynch.api.view.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmfabiano
 */
@Entity
@XmlRootElement
@Table(name = "XXX_carga")
@SqlResultSetMapping(name = "cargaMapping", entities = { @EntityResult(entityClass = Pendiente.class, fields = {
		@FieldResult(name = "id", column = "id"), @FieldResult(name = "afiliadoNombre", column = "afiliadoNombre"),
		@FieldResult(name = "afiliadoApellido", column = "afiliadoApellido"),
		@FieldResult(name = "afiliadoNumero", column = "afiliadoNumero"),
		@FieldResult(name = "especialidad", column = "especialidad"),
		@FieldResult(name = "nombreProfesional", column = "nombreProfesional") }) })
public class Pendiente implements Serializable {

	private static final long serialVersionUID = 4334075941492891215L;

	@Id
	private Long id;

	private String afiliadoNombre;

	private String afiliadoApellido;

	private String afiliadoNumero;

	private String especialidad;

	private String nombreProfesional;

	public Pendiente() {
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getNombreProfesional() {
		return nombreProfesional;
	}

	public void setNombreProfesional(String nombreProfesional) {
		this.nombreProfesional = nombreProfesional;
	}

	public String getAfiliadoNumero() {
		return afiliadoNumero;
	}

	public void setAfiliadoNumero(String afiliadoNumero) {
		this.afiliadoNumero = afiliadoNumero;
	}

}
