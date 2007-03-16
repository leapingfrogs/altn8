package com.leapingfrogs.idea.plugins.alternate.action.support;

import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.RubyString;
import org.jruby.RubyArray;
import org.jruby.runtime.builtin.IRubyObject;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

public class JRubyRunner
{
    private IRuby ruby;
    private IRubyObject instance;

    public JRubyRunner(URI sourceURI, String className)
    {
        ruby = Ruby.getDefaultInstance();
        ruby.loadFile(new File(sourceURI), false);
        instance = ruby.getClass(className).newInstance(new IRubyObject[0]);
    }

    public IRubyObject send(String method)
    {
        return instance.callMethod(ruby.getCurrentContext(), method);
    }

    public IRubyObject send(String method, IRubyObject[] arguments)
    {
        return instance.callMethod(ruby.getCurrentContext(), method, arguments);
    }

    public RubyString newString(String value)
    {
        return RubyString.newString(ruby, value);
    }

    public String[] rubyObjectAsStringArray(IRubyObject iRubyObject)
    {
        RubyArray alternateFileNamesRuby = (RubyArray) iRubyObject;
        Object[] objects = alternateFileNamesRuby.toArray();
        return Arrays.asList(objects).toArray(new String[0]);
    }
}
