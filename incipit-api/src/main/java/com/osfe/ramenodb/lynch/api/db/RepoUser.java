package com.osfe.ramenodb.lynch.api.db;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.osfe.ramenodb.lynch.api.core.OpenLDAP;
import com.osfe.ramenodb.lynch.api.core.User;

/**
*
* @author jmfabiano
*/
public class RepoUser extends GenericRepo<User, Serializable> {

	public RepoUser(SessionFactory factory) {
		super(factory);
	}

	public Optional<User> findByUniqueName(String uniqueName) {
		Optional<User> foundUser = OpenLDAP.findUserByUniqueName(uniqueName);
		return foundUser;
	}

	public Optional<User> validateUserByNameAndPassword(final String username, final String password) {
		return OpenLDAP.validateUserByNameAndPassword(username, password);
	}
}
