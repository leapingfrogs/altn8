package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.action.support.AlternateFilePopupChooser;
import com.leapingfrogs.idea.plugins.alternate.action.support.FindAndOpenFileContentIterator;
import com.leapingfrogs.idea.plugins.alternate.action.support.OpenFileHandler;

import java.util.Arrays;

public abstract class BaseFileAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        processFileEvent(getCurrentFile(e), getModule(e), getProject(e));
    }

    protected abstract void processFileEvent(VirtualFile currentFile, Module currentModule, Project currentProject);

    private VirtualFile getCurrentFile(AnActionEvent e) {
        return ((VirtualFile) e.getDataContext().getData(DataConstants.VIRTUAL_FILE));
    }

    private Project getProject(AnActionEvent e) {
        return (Project) e.getDataContext().getData(DataConstants.PROJECT);
    }

    private Module getModule(AnActionEvent e) {
        return (Module) e.getDataContext().getData(DataConstants.MODULE);
    }

    protected void openFiles(VirtualFile[] virtualFiles, Project currentProject) {
        if (virtualFiles.length == 0) return;

        if (virtualFiles.length == 1)
            openFile(virtualFiles[0], currentProject);
        else
            promptForFilesToOpen(virtualFiles, currentProject);
    }

    protected void openFiles(String[] alternateFileNames, Project currentProject, Module currentModule) {
        VirtualFile[] filesInProject = findFilesInProject(alternateFileNames, currentProject, currentModule);
        openFiles(filesInProject, currentProject);
    }

    private void promptForFilesToOpen(VirtualFile[] virtualFiles, Project currentProject) {
        AlternateFilePopupChooser alternateFilePopupChooser = new AlternateFilePopupChooser(currentProject, new OpenFileHandler(currentProject));
        alternateFilePopupChooser.promptForFiles(virtualFiles);
    }

    private void openFile(VirtualFile virtualFile, Project currentProject) {
        if (virtualFile != null) {
            FileEditorManager fileEditorManager = FileEditorManager.getInstance(currentProject);
            fileEditorManager.openFile(virtualFile, true);
        }
    }

    private VirtualFile[] findFilesInProject(String[] fileNames, Project project, Module module) {
        ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();

        FindAndOpenFileContentIterator fileFinder = new FindAndOpenFileContentIterator(Arrays.asList(fileNames), module, projectFileIndex);
        return fileFinder.getMatchingFiles();
    }
}
