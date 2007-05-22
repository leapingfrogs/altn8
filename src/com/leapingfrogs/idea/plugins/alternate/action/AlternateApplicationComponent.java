package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AlternateApplicationComponent implements ApplicationComponent, Configurable, JDOMExternalizable
{
    protected List mappings = new ArrayList();
    private AlternateConfiguration form;
    private static final String ICON_FILE = "altn8.png";

    public AlternateApplicationComponent()
    {
    }

    public void initComponent()
    {
    }

    public void disposeComponent()
    {
    }

    public String getComponentName()
    {
        return "AlternateApplicationComponent";
    }

    public String getDisplayName()
    {
        return "AltN8";
    }

    public Icon getIcon()
    {
        return new ImageIcon( AlternateApplicationComponent.class.getClassLoader().getResource( ICON_FILE ) );
    }

    public String getHelpTopic()
    {
        return null;
    }

    public JComponent createComponent()
    {
        if ( form == null )
        {
            form = new AlternateConfiguration();
        }
        return form.getRootComponent();
    }

    public boolean isModified()
    {
        return form != null && form.isModified( this );
    }

    public void apply() throws ConfigurationException
    {
        if ( form != null )
        {
            form.getData( this );
        }
    }

    public void reset()
    {
        if ( form != null )
        {
            form.setData( this );
        }
    }

    public void disposeUIResources()
    {
        form = null;
    }

    public List getMappings()
    {
        return mappings;
    }

    public void readExternal( Element element ) throws InvalidDataException
    {
        Element alternateMappingsElement = element.getChild( "alternateMappings" );

        if ( alternateMappingsElement != null )
        {
            List mappingElements = alternateMappingsElement.getChildren();
            for ( int i = 0; i < mappingElements.size(); i++ )
            {
                Element mappingElement = (Element) mappingElements.get( i );
                mappings.add( new AlternateMapping( mappingElement ) );
            }
        }

        if ( mappings.isEmpty() )
        {
            mappings.add( new AlternateMapping( "^Test(.*?)\\.java$", "$1.java" ) );
            mappings.add( new AlternateMapping( "^(.*?)\\.java$", "Test$1.java" ) );
        }
    }

    public void writeExternal( Element element ) throws WriteExternalException
    {
        Element alternateMappingsElement = new Element( "alternateMappings" );
        element.addContent( alternateMappingsElement );

        for ( int i = 0; i < mappings.size(); i++ )
        {
            AlternateMapping mapping = (AlternateMapping) mappings.get( i );
            alternateMappingsElement.addContent( mapping.asJDomElement() );
        }
    }

}
