/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osfe.ramenodb.lynch.api.db;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.osfe.ramenodb.lynch.api.model.Practica;

/**
*
* @author jmfabiano
*/
public class RepoPractica extends GenericRepo<Practica, Serializable> {

	public RepoPractica(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
