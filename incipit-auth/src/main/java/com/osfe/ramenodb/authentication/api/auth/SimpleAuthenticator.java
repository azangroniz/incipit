package com.osfe.ramenodb.authentication.api.auth;

import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.osfe.ramenodb.authentication.api.core.User;
import com.osfe.ramenodb.authentication.api.db.RepoUser;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jmfabiano
 */
public class SimpleAuthenticator implements Authenticator<String, User> {


    private final RepoUser dao;

    public SimpleAuthenticator(RepoUser dao) {
        this.dao = dao;
    }

    @Override
    public Optional<User> authenticate(String jwtoken) throws AuthenticationException {
        if (StringUtils.isBlank(jwtoken)) {
            return Optional.absent();
        } else {
            JWTClaimsSet claimSet = null;
            try {
                claimSet = (JWTClaimsSet) AuthUtils.decodeToken(jwtoken);
            } catch (ParseException e) {
                return Optional.absent();
            } catch (JOSEException e) {
                return Optional.absent();
            }

            // ensure that the token is not expired
            if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
                return Optional.absent();
            } else {
                String subject = null;
                try {
                    subject = AuthUtils.getSubject(jwtoken);
                } catch (ParseException ex) {
                    Logger.getLogger(SimpleAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JOSEException ex) {
                    Logger.getLogger(SimpleAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
                }
                Optional<User> authenticatedUser = dao.findById(Long.parseLong(subject));
                if (authenticatedUser == null) {
                    return Optional.absent();
                }
                return authenticatedUser;
            }
        }
    }
}
