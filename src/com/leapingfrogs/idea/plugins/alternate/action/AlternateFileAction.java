package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.action.support.AlternateFinder;
import com.leapingfrogs.idea.plugins.alternate.action.support.JavaAlternateFinder;

public class AlternateFileAction extends BaseFileAction
{
    private final AlternateFinder alternateFinder;

    public AlternateFileAction()
    {
        Application application = ApplicationManager.getApplication();
        alternateFinder = new JavaAlternateFinder( (AlternateApplicationComponent) application.getComponent( AlternateApplicationComponent.class ) );
    }

    protected void processFileEvent( VirtualFile currentFile, Module currentModule, Project currentProject )
    {
        if ( currentFile != null )
        {
            String[] alternateFileNames = alternateFinder.getAlternateNames( currentFile.getName() );
            openFiles( alternateFileNames, currentProject, currentModule );
        }
    }
}
