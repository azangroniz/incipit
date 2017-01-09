package ramenodb.authprovider.db;

import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.SessionFactory;

import ramenodb.authprovider.core.User;
import ramenodb.authprovider.core.User.Provider;
import com.google.common.base.Optional;
import java.io.Serializable;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import ramenodb.authprovider.core.ActiveDirectory;

public class UserDAO extends GenericDAO<User,Serializable> {

    public UserDAO(SessionFactory factory) {
        super(factory);
    }

//    public Optional<User> findById(Long id) {
//        return Optional.fromNullable(get(id));
//    }

    public Optional<User> findByUniqueName(String uniqueName) {
        User foundUser = (User) namedQuery("User.findByUniqueName")
                .setParameter("uniqueName", uniqueName)
                .uniqueResult();
        return Optional.fromNullable(foundUser);
    }

    public Optional<User> findByProvider(Provider provider, String providerId) {
        User foundUser = (User) namedQuery(String.format("User.findBy%s", provider.capitalize()))
                .setParameter(provider.getName(), providerId)
                .uniqueResult();
        return Optional.fromNullable(foundUser);
    }
   

    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

    public Optional<User> findUserByUsernameAndPassword(final String username, final String password) throws NamingException {
        LdapContext ctx = null;
        try {
            ctx = ActiveDirectory.getConnection(username, password);
            if (ctx == null){
                return Optional.absent();
            }
            Optional<User> user = ActiveDirectory.getUser(username, ctx);
            ctx.close();
            return user;
        } catch (NamingException e) {
            ctx.close();
            return Optional.absent();
        }
    }

}
