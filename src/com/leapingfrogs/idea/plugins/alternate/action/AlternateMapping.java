package com.leapingfrogs.idea.plugins.alternate.action;

import org.jdom.Element;

public class AlternateMapping
{
    public static final int MATCH_EXPRESSION = 0;
    public static final int REPLACE_EXPRESSION = 1;

    private final String matchExpression;
    private final String replaceExpression;

    public AlternateMapping( String matchExpression, String replaceExpression )
    {

        this.matchExpression = matchExpression;
        this.replaceExpression = replaceExpression;
    }

    public AlternateMapping( Element mappingElement )
    {
        this.matchExpression = mappingElement.getAttributeValue( "matchExpression" );
        this.replaceExpression = mappingElement.getAttributeValue( "replaceExpression" );
    }

    public String getMatchExpression()
    {
        return matchExpression;
    }

    public String getReplaceExpression()
    {
        return replaceExpression;
    }

    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        AlternateMapping that = (AlternateMapping) o;

        return !( matchExpression != null ? !matchExpression.equals( that.matchExpression ) : that.matchExpression != null ) && !( replaceExpression != null ? !replaceExpression.equals( that.replaceExpression ) : that.replaceExpression != null );
    }

    public int hashCode()
    {
        int result;
        result = ( matchExpression != null ? matchExpression.hashCode() : 0 );
        result = 31 * result + ( replaceExpression != null ? replaceExpression.hashCode() : 0 );
        return result;
    }

    Element asJDomElement()
    {
        Element mappingElement = new Element( "mapping" );
        mappingElement.setAttribute( "matchExpression", getMatchExpression() );
        mappingElement.setAttribute( "replaceExpression", getReplaceExpression() );
        return mappingElement;
    }


    public String toString()
    {
        return "Match: " + matchExpression + " Replace: " + replaceExpression;
    }
}
