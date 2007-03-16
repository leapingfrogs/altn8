package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.action.support.FileHandler;

import javax.swing.*;

public class RunnableFileHandler implements Runnable
{
    private JList list;
    private FileHandler fileHandler;

    public RunnableFileHandler(JList valueList, FileHandler fileHandler)
    {
        this.list = valueList;
        this.fileHandler = fileHandler;
    }

    public void run()
    {
        Object[] valueList = list.getSelectedValues();
        for (Object value : valueList)
        {
            fileHandler.processFile((VirtualFile) value);
        }

    }
}
