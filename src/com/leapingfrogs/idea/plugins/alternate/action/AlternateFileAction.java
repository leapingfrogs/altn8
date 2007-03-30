package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.action.support.AlternateFinder;
import com.leapingfrogs.idea.plugins.alternate.action.support.JavaAlternateFinder;

public class AlternateFileAction extends BaseFileAction {
    private AlternateFinder alternateFinder;

    public AlternateFileAction() {
        alternateFinder = new JavaAlternateFinder();
    }

    protected void processFileEvent(VirtualFile currentFile, Module currentModule, Project currentProject) {
        String[] alternateFileNames = alternateFinder.getAlternateNames(currentFile.getName());
        openFiles(alternateFileNames, currentProject, currentModule);
    }
}
