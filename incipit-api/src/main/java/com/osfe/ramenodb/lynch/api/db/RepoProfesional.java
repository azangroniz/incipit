package com.osfe.ramenodb.lynch.api.db;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.osfe.ramenodb.lynch.api.model.Profesional;

/**
*
* @author jmfabiano
*/
public class RepoProfesional extends GenericRepo<Profesional, Serializable> {

	public RepoProfesional(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
