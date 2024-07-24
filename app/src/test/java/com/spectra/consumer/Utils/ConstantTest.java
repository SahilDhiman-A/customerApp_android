package com.spectra.consumer.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ConstantTest {
    Constant constant;

    @Before
    public void setUp() {
        constant=new Constant();
    }

    @After
    public void tearDown() {
        constant=null;
    }

    @Test
    public void isPositive() {
        assertTrue(constant.isPositive(-2));
    }
    @Test
    public void isPositive1() {
        assertTrue(constant.isPositive(1));
    }
    @Test
    public void isPositive2() {
        assertTrue(constant.isPositive(2));
    }
    @Test
    public void deleteFile() {
        assertTrue(constant.deleteFile("Constant.rootPath"));
    }
    @Test
    public void testIsValidEmailId() {
        assertTrue(Constant.isValidEmailId("email@gmailcom"));
    }
    @Test
    public void isValidPassword() {
        assertTrue(Constant.isValidPassword("123224@GH"));
    }
}
//https://affle.atlassian.net/secure/Dashboard.jspa?selectPageId=15257