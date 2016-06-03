/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.antonio.samir.leichtforponto.util.DateUtil;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samirmoreira
 */
public class DataTest {

    public DataTest() {
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

    @Test
    public void dateTest() throws ParseException {

        final String dateString = "14/05/2016";

        final Date parseDate = DateUtils.parseDate(dateString, "dd/MM/yyyy");
        
        final String stringId = DateUtil.getStringId(parseDate);
        
        assertEquals("20160514", stringId);

    }
}
