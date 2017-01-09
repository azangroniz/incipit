///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.osfe.ramenodb.lynch.api.Model.Visita;
//import com.osfe.ramenodb.lynch.api.view.model.Reporte;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import jdk.nashorn.internal.parser.JSONParser;
//import net.minidev.json.JSONObject;
//import net.sf.jxls.exception.ParsePropertyException;
//import net.sf.jxls.transformer.XLSTransformer;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author jmfabiano
// */
//public class TestingClienteJXLS {
//    private static String templateFileName = "src/main/resources/templates/XAfiliados.xls";
//    private static String destFileName = "afiliados_output.xls";
//
//    public TestingClienteJXLS() {
//       
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    @Test
//    public void hello() throws ParsePropertyException, InvalidFormatException {
//        try {
//
//            URL url = new URL("http://101.101.56.150:1010/v1/visita/10");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                Reporte ob = new ObjectMapper().readValue(output, Reporte.class);
//              
//                System.out.println(output);
//                
//                Map beans = new HashMap();
//        beans.put("reporte", ob);
//        XLSTransformer transformer = new XLSTransformer();
//        transformer.markAsFixedSizeCollection("employee");
//        transformer.transformXLS(templateFileName, beans, destFileName);
//                
//            }
//
//            conn.disconnect();
//
//        } catch (MalformedURLException e) {
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//    }
//}
