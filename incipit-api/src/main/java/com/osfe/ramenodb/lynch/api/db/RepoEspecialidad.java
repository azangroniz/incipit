package com.osfe.ramenodb.lynch.api.db;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.osfe.ramenodb.lynch.api.model.Especialidad;

/**
*
* @author jmfabiano
*/
public class RepoEspecialidad extends GenericRepo<Especialidad, Serializable> {

	public RepoEspecialidad(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
