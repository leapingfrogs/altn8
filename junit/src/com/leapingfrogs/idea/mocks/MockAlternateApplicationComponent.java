package com.leapingfrogs.idea.mocks;

import com.leapingfrogs.idea.plugins.alternate.action.AlternateApplicationComponent;

import java.util.List;

public class MockAlternateApplicationComponent extends AlternateApplicationComponent
{
    public MockAlternateApplicationComponent( List mappings )
    {
        super.mappings = mappings;
    }
}
