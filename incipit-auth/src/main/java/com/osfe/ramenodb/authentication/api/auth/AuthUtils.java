package com.osfe.ramenodb.authentication.api.auth;

import java.text.ParseException;

import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.osfe.ramenodb.authentication.api.core.Token;
import com.osfe.ramenodb.authentication.api.core.User;

/**
 *
 * @author jmfabiano
 */
public final class AuthUtils {

	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	private static final String TOKEN_SECRET = "FabianoJoseMariaElCrack";
	public static final String AUTH_HEADER_KEY = "Authorization";

	public static String getSubject(String authHeader) throws ParseException, JOSEException {
		return decodeToken(authHeader).getSubject();
	}


	public static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
		SignedJWT signedJWT = null;
		if (authHeader.startsWith("Bearer")) {
			signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
		} else {
			signedJWT = SignedJWT.parse(authHeader);
		}
		if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
			return signedJWT.getJWTClaimsSet();
		} else {
			throw new JOSEException("Signature verification failed");
		}
	}

	public static Token createToken(String host, User user) throws JOSEException {
		JWTClaimsSet claim = new JWTClaimsSet();
		claim.setSubject(Long.toString(user.getId()));
		for (int i = 0; i < user.getRoles().size(); i++) {
			claim.setClaim("ROL" + i + "", user.getRoles().get(i));
		}
		claim.setIssuer(host);
		claim.setIssueTime(DateTime.now().toDate());
		claim.setExpirationTime(DateTime.now().plusHours(8).toDate());

		JWSSigner signer = new MACSigner(TOKEN_SECRET);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);

		return new Token(jwt.serialize());
	}

	public static Token createToken(String host, long sub) throws JOSEException {
		JWTClaimsSet claim = new JWTClaimsSet();
		claim.setSubject(Long.toString(sub));
		claim.setIssuer(host);
		claim.setIssueTime(DateTime.now().toDate());
		claim.setExpirationTime(DateTime.now().plusDays(14).toDate());

		JWSSigner signer = new MACSigner(TOKEN_SECRET);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);

		return new Token(jwt.serialize());
	}

	public static String getSerializedToken(String authHeader) {
		return authHeader.split(" ")[1];
	}
}
