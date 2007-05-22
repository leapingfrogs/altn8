package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

class RunnableFileHandler implements Runnable
{
    private final JList list;
    private final FileHandler fileHandler;

    public RunnableFileHandler( JList valueList, FileHandler fileHandler )
    {
        this.list = valueList;
        this.fileHandler = fileHandler;
    }

    public void run()
    {
        Object[] valueList = list.getSelectedValues();
        for ( int i = 0; i < valueList.length; i++ )
        {
            fileHandler.processFile( (VirtualFile) valueList[i] );
        }

    }
}
