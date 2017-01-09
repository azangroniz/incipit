/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldap.test;

import com.google.common.base.Optional;
import com.osfe.ramenodb.authentication.api.core.OpenLDAP;
import com.osfe.ramenodb.authentication.api.core.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jmfabiano
 */
public class Ldapgrupocheck {
    
    public Ldapgrupocheck() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testForGroups() {
//        Optional<User> u = OpenLDAP.validateUserByNameAndPassword("usuario1","usuario1");
         System.out.println(OpenLDAP.findGroup("usuario1","intranet-noticias"));
     }
}
