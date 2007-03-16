package com.leapingfrogs.idea.plugins.alternate.action.support;

import junit.framework.TestCase;

import java.net.URI;
import java.net.URISyntaxException;

import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.RubyArray;

public class TestJRubyRunner extends TestCase
{
    public void testSimpleCall() throws URISyntaxException
    {
        URI sourceFile = JRubyRunner.class.getClassLoader().getResource("altn8.rb").toURI();
        String className = "AltN8";
        JRubyRunner runner = new JRubyRunner(sourceFile, className);
        IRubyObject alternateFileNameRuby = runner.send("alternate_for", new IRubyObject[] {runner.newString("Wibble.sp"), runner.newString("PLAIN_TEXT")});
        assertTrue(alternateFileNameRuby instanceof RubyArray);
        RubyArray array = (RubyArray)alternateFileNameRuby;
        assertEquals(2, array.size());

        assertEquals("test_Wibble.xml", array.get(0));
        assertEquals("test_Wibble.rb", array.get(1));
    }
}
