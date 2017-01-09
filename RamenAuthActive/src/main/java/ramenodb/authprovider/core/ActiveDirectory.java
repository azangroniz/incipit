package ramenodb.authprovider.core;

import com.google.common.base.Optional;
import ramenodb.authprovider.core.User;
import java.nio.ByteBuffer;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.InitialLdapContext;
import javax.net.ssl.*;

//******************************************************************************
//**  ActiveDirectory
//*****************************************************************************/
/**
 * Provides static methods to authenticate users, change passwords, etc.
 *
 *****************************************************************************
 */
public class ActiveDirectory {

    private static String[] userAttributes = {
        "distinguishedName", "cn", "name", "uid",
        "sn", "givenname", "memberOf", "samaccountname",
        "userPrincipalName", "objectclass", "objectSid", "objectGUID"
    };

    private ActiveDirectory() {
    }

    //**************************************************************************
    //** getConnection
    //*************************************************************************/
    /**
     * Used to authenticate a user given a username/password. Domain name is
     * derived from the fully qualified domain name of the host machine.
     */
    public static LdapContext getConnection(String username, String password) throws NamingException {
        return getConnection(username, password, null, null);
    }

    //**************************************************************************
    //** getConnection
    //*************************************************************************/
    /**
     * Used to authenticate a user given a username/password and domain name.
     * Provides an option to identify a specific a Active Directory server.
     */
    public static LdapContext getConnection(String username, String password, String domainName, String serverName) throws NamingException {

        if (domainName == null) {
            try {
                String fqdn = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                if (fqdn.split("\\.").length > 1) {
                    domainName = fqdn.substring(fqdn.indexOf(".") + 1);
                }
            } catch (java.net.UnknownHostException e) {
            }
        }

        //System.out.println("Authenticating " + username + "@" + domainName + " through " + serverName);
        if (password != null) {
            password = password.trim();
            if (password.length() == 0) {
                password = null;
            }
        }

        //bind by using the specified username/password
        Hashtable props = new Hashtable();
        sanitizeUsername(username);
        String principalName = username + "@" + domainName;
        props.put(Context.SECURITY_PRINCIPAL, principalName);
        if (password != null) {
            props.put(Context.SECURITY_CREDENTIALS, password);
        }

        String ldapURL = "ldap://" + ((serverName == null) ? domainName : serverName + "." + domainName) + '/';
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, ldapURL);
        props.put("java.naming.ldap.attributes.binary", "objectGUID");
        try {
            return new InitialLdapContext(props, null);
        } catch (javax.naming.CommunicationException e) {
//            throw new NamingException("Failed to connect to " + domainName + ((serverName == null) ? "" : " through " + serverName));
            return null;
        } catch (NamingException e) {            
//            throw new NamingException("Failed to authenticate " + username + "@" + domainName + ((serverName == null) ? "" : " through " + serverName));
            return null;
        }
    }

    //**************************************************************************
    //** getUser
    //*************************************************************************/
    /**
     * Used to check whether a username is valid.
     *
     * @param username A username to validate (e.g. "peter", "peter@acme.com",
     * or "ACME\peter").
     */
    public static Optional<User> getUser(String username, LdapContext context) {
        try {
            String domainName = null;
            sanitizeUsername(username);
            if (username.contains("@")) {
                username = username.substring(0, username.indexOf("@"));
                domainName = username.substring(username.indexOf("@") + 1);
            } else if (username.contains("\\")) {
                username = username.substring(0, username.indexOf("\\"));
                domainName = username.substring(username.indexOf("\\") + 1);
            } else {
                String authenticatedUser = (String) context.getEnvironment().get(Context.SECURITY_PRINCIPAL);
                if (authenticatedUser.contains("@")) {
                    domainName = authenticatedUser.substring(authenticatedUser.indexOf("@") + 1);
                }
            }

            if (domainName != null) {
                String principalName = username + "@" + domainName;
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SUBTREE_SCOPE);
                controls.setReturningAttributes(userAttributes);
                NamingEnumeration<SearchResult> answer = context.search(toDC(domainName), "(& (userPrincipalName=" + principalName + ")(objectClass=user))", controls);
                if (answer.hasMore()) {
                    Attributes attr = answer.next().getAttributes();
                    Attribute user = attr.get("userPrincipalName");
                    if (user != null) {
                        return LdapFullUserToSimpleUser(new LdapFullUser(attr));

                    }
                }
            }
        } catch (NamingException e) {
            //e.printStackTrace();
        }
        return null;
    }

    private static String toDC(String domainName) {
        StringBuilder buf = new StringBuilder();
        for (String token : domainName.split("\\.")) {
            if (token.length() == 0) {
                continue;   // defensive check
            }
            if (buf.length() > 0) {
                buf.append(",");
            }
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }

    //**************************************************************************
    //** User Class
    //*************************************************************************/
    /**
     * Used to represent a User in Active Directory
     */
    public static class LdapFullUser {

        private String samaccountname;
        private String userPrincipal;
        private String givenName;
        private List<String> groups = new ArrayList<>();
        private String objectGUID;

        public LdapFullUser(Attributes attr) throws javax.naming.NamingException {
            samaccountname = (String) attr.get("samaccountname").get();
            userPrincipal = (String) attr.get("userPrincipalName").get();
            givenName = (String) attr.get("givenname").get();
            byte[] guid = (byte[]) attr.get("objectGUID").get();
            objectGUID = convertToDashedString(guid);
            //Listing Groups
            for (int i = 0; i < attr.get("memberof").size(); i++) {
                groups.add((String) attr.get("memberof").get(i));
            }
        }

        public String getSamaccountname() {
            return samaccountname;
        }

        public String getUserPrincipal() {
            return userPrincipal;
        }

        public List<String> getGroups() {
            return groups;
        }

        public String getObjectGUID() {
            return objectGUID;
        }

        public String getGivenName() {
            return givenName;
        }


        private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        private static TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        private byte[] getPassword(String newPass) {
            String quotedPassword = "\"" + newPass + "\"";
            //return quotedPassword.getBytes("UTF-16LE");
            char unicodePwd[] = quotedPassword.toCharArray();
            byte pwdArray[] = new byte[unicodePwd.length * 2];
            for (int i = 0; i < unicodePwd.length; i++) {
                pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
                pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
            }
            return pwdArray;
        }
    }

    public static String convertToBindingString(byte[] objectGUID) {
        StringBuilder displayStr = new StringBuilder();

        displayStr.append("<GUID=");
        displayStr.append(convertToDashedString(objectGUID));
        displayStr.append(">");

        return displayStr.toString();
    }

    public static String convertToDashedString(byte[] objectGUID) {
        StringBuilder displayStr = new StringBuilder();

        displayStr.append(prefixZeros((int) objectGUID[3] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[2] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[1] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[0] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[5] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[4] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[7] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[6] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[8] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[9] & 0xFF));
        displayStr.append("-");
        displayStr.append(prefixZeros((int) objectGUID[10] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[11] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[12] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[13] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[14] & 0xFF));
        displayStr.append(prefixZeros((int) objectGUID[15] & 0xFF));

        return displayStr.toString();
    }

    private static String prefixZeros(int value) {
        if (value <= 0xF) {
            StringBuilder sb = new StringBuilder("0");
            sb.append(Integer.toHexString(value));

            return sb.toString();

        } else {
            return Integer.toHexString(value);
        }
    }

    public static String NormalizeString(String toNormalize) {
        String normalizedName = Normalizer.normalize(toNormalize, Normalizer.Form.NFD);
        normalizedName = normalizedName.replaceAll("[^\\p{ASCII}]", "");
        return normalizedName;
    }

    private static String sanitizeUsername(String username) {
        return username.replaceAll("[^A-Za-z0-9-_.]", "");
    }

    private static Optional<User> LdapFullUserToSimpleUser(LdapFullUser ldapUser) {
        User user = new User();
        user.setUniqueName(ldapUser.samaccountname);
        user.setDisplayName(ldapUser.givenName);
        for (int i = 0; i < ldapUser.getGroups().size(); i++) {
            String[] spliter = ldapUser.getGroups().get(i).split("CN=");
            String splited = spliter[1].split(",")[0];
            user.getRoles().add(NormalizeString(splited));
        }
        return Optional.fromNullable(user);
    }

    public static Optional<User> findUserByName(String username) {
        
        String domainName = "PRINCIPAL.local";
        String principalName = username + "@" + domainName;
        
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://PRINCIPAL.local/");   //Just change this to the actual IP and Port
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put("java.naming.ldap.attributes.binary", "objectGUID");    //Important!
        env.put(Context.SECURITY_PRINCIPAL, "testing@PRINCIPAL.local");
        env.put(Context.SECURITY_CREDENTIALS, "Testing01!");

        DirContext ctx;

        try {
            //Authenticate the logon user
            ctx = new InitialDirContext(env);

            //Start checking if the user is within the organization unit(s)
            String searchBase = toDC(domainName);
            String searchFilter = "(& (userPrincipalName=" + principalName + ")(objectClass=user))";
            String[] returningAttrs = {
                "givenname", "memberOf", "samaccountname",
                "userPrincipalName", "objectGUID"
            };
            SearchControls sCtrl = new SearchControls();
            sCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
            sCtrl.setReturningAttributes(returningAttrs);

            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, sCtrl);

            if (answer.hasMore()) {
                Attributes attr = answer.next().getAttributes();
                Attribute user = attr.get("userPrincipalName");
                if (user != null) {
                    return LdapFullUserToSimpleUser(new LdapFullUser(attr));

                }
            }
        } catch (NamingException ex) {
            // Do something with the exception...
        }
        return null;
    }
}
