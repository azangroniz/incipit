package com.osfe.ramenodb.lynch.api.db;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.osfe.ramenodb.lynch.api.model.Visita;

/**
*
* @author jmfabiano
*/
public class RepoVisita extends GenericRepo<Visita, Serializable> {

	public RepoVisita(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
