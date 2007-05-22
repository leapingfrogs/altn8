package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.leapingfrogs.idea.mocks.MockAlternateApplicationComponent;
import com.leapingfrogs.idea.plugins.alternate.action.AlternateApplicationComponent;
import com.leapingfrogs.idea.plugins.alternate.action.AlternateMapping;
import junit.framework.TestCase;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TestJavaAlternateFinder extends TestCase
{
    private AlternateFinder testee;
    private List mappings = new ArrayList();

    protected void setUp() throws Exception
    {
        AlternateApplicationComponent alternateApplicationComponent = new MockAlternateApplicationComponent( mappings );
        testee = new JavaAlternateFinder( alternateApplicationComponent );
    }

    public void testGetAlternateNames() throws URISyntaxException
    {
        mappings.add( new AlternateMapping( "^test_(.*?)\\.xml", "$1.sp" ) );
        mappings.add( new AlternateMapping( "^test_(.*?)\\.rb", "$1.sp" ) );
        mappings.add( new AlternateMapping( "^(.*?)\\.sp$", "test_$1.xml" ) );
        mappings.add( new AlternateMapping( "^(.*?)\\.sp$", "test_$1.rb" ) );

        String[] names = testee.getAlternateNames( "wibble.sp" );
        assertEquals( 2, names.length );
        assertEquals( "test_wibble.xml", names[0] );
        assertEquals( "test_wibble.rb", names[1] );

        names = testee.getAlternateNames( "test_wibble.rb" );
        assertEquals( 1, names.length );
        assertEquals( "wibble.sp", names[0] );

        names = testee.getAlternateNames( "test_wibble.xml" );
        assertEquals( 1, names.length );
        assertEquals( "wibble.sp", names[0] );

    }
}
