package com.osfe.ramenodb.authentication.api.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Optional<User> findByUniqueName(String uniqueName) {
		 User foundUser = (User) namedQuery("User.findByUniqueName")
		 .setParameter("uniqueName", uniqueName)
		 .uniqueResult();
//		for bypassing the login
//		Optional<User> foundUser = OpenLDAP.returnDefaultUser();

		return Optional.fromNullable(foundUser);
	}

	public Optional<User> findByProvider(Provider provider, String providerId) {
		User foundUser = (User) namedQuery(String.format("User.findBy%s", provider.capitalize()))
				.setParameter(provider.getName(), providerId).uniqueResult();
		return Optional.fromNullable(foundUser);
	}

	public List<User> findAll() {
		return list(namedQuery("User.findAll"));
	}
	
	final static Map<Long, User> userTable = new HashMap<>();

	static {
		userTable.put(1l, new User("alice", "secret"));
		userTable.put(2l, new User ("bob", "letMeIn"));
	}

	public Optional<User> findUserByUsernameAndPassword(final String username, final String password) {
		for (Map.Entry<Long, User> entry : userTable.entrySet()) {
			User user = entry.getValue();
			if (user.getPassword().equals(password) && user.getUniqueName().equals(username)) {
				return Optional.of(user);
			}
		}
		return Optional.absent();
	}
	
}
