package com.osfe.ramenodb.lynch.api.core;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.google.common.base.Optional;

/**
 *
 * @author jmfabiano
 */
public class OpenLDAP {

	private static final String LDAP_SERVER = "ldap://sist53:389/dc=osfe,dc=org,dc=ar";

	public static Optional<User> validateUserByNameAndPassword(String username, String password) {
		LdapContext ctx = null;
		Optional<User> foundUser = null;
		try {
			ctx = new InitialLdapContext(setCustomCtxEnv(username, password), null);
			foundUser = OpenLDAP.findUserByUniqueName(ctx, username);
			ctx.close();
		} catch (NamingException e) {
			return Optional.absent();
		}
		return foundUser;
	}

	public static Optional<User> findUserByUniqueName(String accountName) {

		// String searchFilter = "(&(objectClass=group)(uid=" + accountName +
		// "))";
		String searchFilter = "(uid=" + accountName + ")";
		// String searchFilter = "(& (uid=" + accountName +
		// ")(objectClass=user))";
		String ldapSearchBase = "";
		// String[] returningAttrs = {
		// "givenname", "memberOf", "samaccountname",
		// "userPrincipalName", "objectGUID"
		// };

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// searchControls.setReturningAttributes(returningAttrs);

		try {
			LdapContext ctx = new InitialLdapContext(setDefaultCtxEnv(), null);
			NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
			SearchResult searchResult = null;
			if (results.hasMoreElements()) {
				searchResult = (SearchResult) results.nextElement();

				// make sure there is not another item available, there should
				// be only 1 match
				if (results.hasMoreElements()) {
					System.err.println("Matched multiple users for the accountName: " + accountName);
					return null;
				}
			}
			return createUser(searchResult);
		} catch (NamingException e) {
			return Optional.absent();
		}
	}

	public static Optional<User> findUserByUniqueName(LdapContext ctx, String accountName) {
		String searchFilter = "(uid=" + accountName + ")";
		String ldapSearchBase = "";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		try {
			NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

			SearchResult searchResult = null;
			if (results.hasMoreElements()) {
				searchResult = (SearchResult) results.nextElement();

				// make sure there is not another item available, there should
				// be only 1 match
				if (results.hasMoreElements()) {
					System.err.println("Matched multiple users for the accountName: " + accountName);
					return null;
				}
			}
			return createUser(searchResult);
		} catch (NamingException e) {
			return Optional.absent();
		}
	}

	private static Optional<User> createUser(SearchResult result) throws NamingException {
		User foundUser = new User();
		foundUser.setUniqueName((String) result.getAttributes().get("uid").get());
		foundUser.setDisplayName((String) result.getAttributes().get("cn").get());
		try {
			foundUser.setEmail((String) result.getAttributes().get("roomnumber").get());
		} catch (NullPointerException e) {
			System.out.println("Sin Email");
		}
		return Optional.of(foundUser);
	}

	public static Hashtable<String, Object> setCustomCtxEnv(String ldapUsername, String ldapPassword) {
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if (ldapUsername != null) {
			env.put(Context.SECURITY_PRINCIPAL, "uid=" + ldapUsername + ",ou=people,dc=osfe,dc=org,dc=ar");
		}
		if (ldapPassword != null) {
			env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
		}
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, OpenLDAP.LDAP_SERVER);
		// ensures that objectSID attribute values
		// will be returned as a byte[] instead of a String
		// env.put("java.naming.ldap.attributes.binary", "objectSID");
		// the following is helpful in debugging errors
		// env.put("com.sun.jndi.ldap.trace.ber", System.err);
		return env;
	}

	public static Hashtable<String, Object> setDefaultCtxEnv() {
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=sistemas,ou=people,dc=osfe,dc=org,dc=ar");
		env.put(Context.SECURITY_CREDENTIALS, "sistemas");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, OpenLDAP.LDAP_SERVER);
		return env;
	}

	// get the SID of the users primary group
	// public static String findGroupBySID(DirContext ctx, String
	// ldapSearchBase, String sid) throws NamingException {
	//
	// String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";
	//
	// SearchControls searchControls = new SearchControls();
	// searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//
	// NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase,
	// searchFilter, searchControls);
	//
	// if(results.hasMoreElements()) {
	// SearchResult searchResult = (SearchResult) results.nextElement();
	//
	// //make sure there is not another item available, there should be only 1
	// match
	// if(results.hasMoreElements()) {
	// System.err.println("Matched multiple groups for the group with SID: " +
	// sid);
	// return null;
	// } else {
	// return (String)searchResult.getAttributes().get("sAMAccountName").get();
	// }
	// }
	// return null;
	// }
	// get the users Primary Group
	// public static String getPrimaryGroupSID(SearchResult srLdapUser) throws
	// NamingException {
	// byte[] objectSID =
	// (byte[])srLdapUser.getAttributes().get("objectSid").get();
	// String strPrimaryGroupID =
	// (String)srLdapUser.getAttributes().get("primaryGroupID").get();
	//
	// String strObjectSid = decodeSID(objectSID);
	//
	// return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) +
	// strPrimaryGroupID;
	// }
	/**
	 * The binary data is in the form: byte[0] - revision level byte[1] - count
	 * of sub-authorities byte[2-7] - 48 bit authority (big-endian) and then
	 * count x 32 bit sub authorities (little-endian)
	 *
	 * The String value is: S-Revision-Authority-SubAuthority[n]...
	 *
	 * Based on code from here -
	 * http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
	 */
	// public static String decodeSID(byte[] sid) {
	//
	// final StringBuilder strSid = new StringBuilder("S-");
	//
	// // get version
	// final int revision = sid[0];
	// strSid.append(Integer.toString(revision));
	//
	// //next byte is the count of sub-authorities
	// final int countSubAuths = sid[1] & 0xFF;
	//
	// //get the authority
	// long authority = 0;
	// //String rid = "";
	// for(int i = 2; i <= 7; i++) {
	// authority |= ((long)sid[i]) << (8 * (5 - (i - 2)));
	// }
	// strSid.append("-");
	// strSid.append(Long.toHexString(authority));
	//
	// //iterate all the sub-auths
	// int offset = 8;
	// int size = 4; //4 bytes for each sub auth
	// for(int j = 0; j < countSubAuths; j++) {
	// long subAuthority = 0;
	// for(int k = 0; k < size; k++) {
	// subAuthority |= (long)(sid[offset + k] & 0xFF) << (8 * k);
	// }
	//
	// strSid.append("-");
	// strSid.append(subAuthority);
	//
	// offset += size;
	// }
	//
	// return strSid.toString();
	// }
}
