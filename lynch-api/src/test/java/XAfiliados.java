///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import com.osfe.ramenodb.lynch.api.view.model.Reporte;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import net.sf.jxls.exception.ParsePropertyException;
//import net.sf.jxls.transformer.Configuration;
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
//public class XAfiliados {
//
//    private static String templateFileName = "src/main/resources/templates/XAfiliados.xls";
//    private static String destFileName = "afiliados_output.xls";
//
//    public XAfiliados() {
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
//    public void hello() throws IOException, ParsePropertyException, InvalidFormatException {
//        Collection informe = new HashSet();
////        informe.add(new Reporte(destFileName, templateFileName, null, Long.MIN_VALUE, destFileName, destFileName, destFileName, destFileName, destFileName, destFileName));
////        staff.add(new net.sf.jxls.sample.model.Employee("Derek", 35, 3000, 0.30));
////        staff.add(new net.sf.jxls.sample.model.Employee("Elsa", 28, 1500, 0.15));
////        staff.add(new net.sf.jxls.sample.model.Employee("Oleg", 32, 2300, 0.25));
////        staff.add(new net.sf.jxls.sample.model.Employee("Neil", 34, 2500, 0.00));
////        staff.add(new net.sf.jxls.sample.model.Employee("Maria", 34, 1700, 0.15));
////        staff.add(new net.sf.jxls.sample.model.Employee("John", 35, 2800, 0.20));
//        Map beans = new HashMap();
//        beans.put("reporte", informe);
//        Configuration config = new Configuration();
////        config.setUTF16( true );
//        XLSTransformer transformer = new XLSTransformer(config);
//        transformer.groupCollection("reporte.nombre");
//        transformer.transformXLS(templateFileName, beans, destFileName);
//    }
//
//}
