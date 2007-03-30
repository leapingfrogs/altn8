package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class OpenFileHandler implements FileHandler {
    private Project currentProject;

    public OpenFileHandler(Project currentProject) {
        this.currentProject = currentProject;
    }

    public void processFile(VirtualFile virtualFile) {
        if (virtualFile != null) {
            FileEditorManager fileEditorManager = FileEditorManager.getInstance(currentProject);
            fileEditorManager.openFile(virtualFile, true);
        }
    }
}
