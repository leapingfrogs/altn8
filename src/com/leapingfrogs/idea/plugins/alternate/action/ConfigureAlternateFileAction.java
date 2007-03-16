package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.application.AlternateApp;

public class ConfigureAlternateFileAction extends BaseFileAction
{
    protected void processFileEvent(VirtualFile currentFile, Module currentModule, Project currentProject)
    {
        try
        {
            VirtualFile virtualFile = VfsUtil.findFileByURL(AlternateApp.getConfigFileURI().toURL());
            openFile(virtualFile, currentProject);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
