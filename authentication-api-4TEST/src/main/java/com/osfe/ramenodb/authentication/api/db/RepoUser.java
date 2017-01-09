package com.osfe.ramenodb.authentication.api.db;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.osfe.ramenodb.authentication.api.core.OpenLDAP;
import com.osfe.ramenodb.authentication.api.core.User;
import com.osfe.ramenodb.authentication.api.core.User.Provider;

/**
 *
 * @author jmfabiano
 */
public class RepoUser extends GenericRepo<User, Serializable> {

	public RepoUser(SessionFactory factory) {
		super(factory);
	}

	public Optional<User> findByUniqueName() {
		// User foundUser = (User) namedQuery("User.findByUniqueName")
		// .setParameter("uniqueName", uniqueName)
		// .uniqueResult();
		Optional<User> foundUser = OpenLDAP.returnDefaultUser();

		return foundUser;
	}

	public Optional<User> findByProvider(Provider provider, String providerId) {
		User foundUser = (User) namedQuery(String.format("User.findBy%s", provider.capitalize()))
				.setParameter(provider.getName(), providerId).uniqueResult();
		return Optional.fromNullable(foundUser);
	}

	public List<User> findAll() {
		return list(namedQuery("User.findAll"));
	}

	public Optional<User> validateOnlyUser() {
		// LdapContext ctx = null;
		// try {
		// ctx = ActiveDirectory.getConnection(username, password);
		// if (ctx == null) {
		// return Optional.absent();
		// }
		// Optional<User> user = ActiveDirectory.getUser(username, ctx);
		// ctx.close();
		// return user;
		// } catch (NamingException e) {
		// ctx.close();
		// return Optional.absent();
		// }
		return OpenLDAP.validateOnlyUser();
	}
}
