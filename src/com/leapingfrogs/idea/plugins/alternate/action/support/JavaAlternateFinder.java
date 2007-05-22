package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.leapingfrogs.idea.plugins.alternate.action.AlternateApplicationComponent;
import com.leapingfrogs.idea.plugins.alternate.action.AlternateMapping;

import java.util.*;

public class JavaAlternateFinder implements AlternateFinder
{
    private final AlternateApplicationComponent applicationComponent;

    public JavaAlternateFinder( AlternateApplicationComponent applicationComponent )
    {
        this.applicationComponent = applicationComponent;
    }

    public String[] getAlternateNames( String currentFileName )
    {
        Map mappings = getMappingsAsHash();

        List names = new ArrayList();

        for ( Iterator iterator = mappings.keySet().iterator(); iterator.hasNext(); )
        {
            String matchPattern = (String) iterator.next();
            List replacePatterns = (List) mappings.get( matchPattern );

            if ( currentFileName.matches( matchPattern ) )
            {
                for ( int i = 0; i < replacePatterns.size(); i++ )
                {
                    String newName = currentFileName.replaceFirst( matchPattern, (String) replacePatterns.get( i ) );
                    names.add( newName );
                }
            }
        }
        return (String[]) names.toArray( new String[0] );
    }

    private Map getMappingsAsHash()
    {
        List mappingList = applicationComponent.getMappings();

        Map mappings = new HashMap();

        for ( int i = 0; i < mappingList.size(); i++ )
        {
            AlternateMapping mapping = (AlternateMapping) mappingList.get( i );
            if ( !mappings.containsKey( mapping.getMatchExpression() ) )
            {
                mappings.put( mapping.getMatchExpression(), new ArrayList() );
            }
            ( (List) mappings.get( mapping.getMatchExpression() ) ).add( mapping.getReplaceExpression() );
        }

        return mappings;
    }
}
