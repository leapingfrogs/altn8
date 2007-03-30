package com.leapingfrogs.idea.plugins.alternate.action.support;

import junit.framework.TestCase;

import java.net.URISyntaxException;

public class TestJavaAlternateFinder extends TestCase {
    private AlternateFinder testee;


    protected void setUp() throws Exception {
        testee = new JavaAlternateFinder();
    }

    public void testGetAlternateNames() throws URISyntaxException {
        String[] names = testee.getAlternateNames("wibble.sp");
        assertEquals(2, names.length);
        assertEquals("test_wibble.xml", names[0]);
        assertEquals("test_wibble.rb", names[1]);

        names = testee.getAlternateNames("test_wibble.rb");
        assertEquals(1, names.length);
        assertEquals("wibble.sp", names[0]);

        names = testee.getAlternateNames("test_wibble.xml");
        assertEquals(1, names.length);
        assertEquals("wibble.sp", names[0]);

    }
}
