package com.leapingfrogs.idea.plugins.alternate.application;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;

import java.net.URI;
import java.net.URISyntaxException;

public class AlternateApp implements ApplicationComponent
{
    public static final String SOURCE_FILE = "altn8.rb";
    public static final String CONFIGURATION_FILE = "altn8.alt";
    public static final String RUBY_CLASS_NAME = "AltN8";

    public void initComponent()
    {
        Application application = ApplicationManager.getApplication();
        application.runWriteAction(new Runnable()
        {
            public void run()
            {
                FileTypeManager instance = FileTypeManager.getInstance();
                FileType textFileType = instance.getFileTypeByExtension("txt");
                instance.associateExtension(textFileType, "alt");
            }
        }
        );
    }

    public void disposeComponent()
    {
    }

    public String getComponentName()
    {
        return "Alternate Support Loader";
    }

    public static URI getConfigFileURI() throws URISyntaxException
    {
        return AlternateApp.class.getClassLoader().getResource(CONFIGURATION_FILE).toURI();
    }

    public static URI getSourceFileURI() throws URISyntaxException
    {
        return AlternateApp.class.getClassLoader().getResource(SOURCE_FILE).toURI();
    }

    public static String getRubyClassName()
    {
        return RUBY_CLASS_NAME;
    }
}
