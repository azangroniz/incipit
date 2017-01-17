package com.osfe.ramenodb.authentication.api.db;

import com.google.common.base.Optional;
import com.osfe.ramenodb.authentication.api.core.User;
import com.osfe.ramenodb.authentication.api.core.User.Provider;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * @author jmfabiano
 */
public class RepoUser extends GenericRepo<User, Serializable> {

    public RepoUser(SessionFactory factory) {
        super(factory);
    }

    public Optional<User> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Optional<User> findByEmail(String email) {
        User foundUser = (User) namedQuery("User.findByEmail")
                .setParameter("email", email)
                .uniqueResult();
        return Optional.fromNullable(foundUser);
    }

    public Optional<User> findByProvider(Provider provider, String providerId) {
        User foundUser = (User) namedQuery(String.format("User.findBy%s", provider.capitalize()))
                .setParameter(provider.getName(), providerId)
                .uniqueResult();
        return Optional.fromNullable(foundUser);
    }

    public User save(User user) {
        return merge(user);
    }

    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }
}
