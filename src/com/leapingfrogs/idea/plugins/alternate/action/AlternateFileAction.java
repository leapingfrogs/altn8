package com.leapingfrogs.idea.plugins.alternate.action;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.action.support.FindAndOpenFileContentIterator;
import com.leapingfrogs.idea.plugins.alternate.action.support.JRubyRunner;
import com.leapingfrogs.idea.plugins.alternate.application.AlternateApp;
import org.jruby.runtime.builtin.IRubyObject;

import java.net.URISyntaxException;
import java.util.Arrays;

public class AlternateFileAction extends BaseFileAction
{
    protected void processFileEvent(VirtualFile currentFile, Module currentModule, Project currentProject)
    {
        try
        {
            String[] alternateFileNames = getAlternateNames(currentFile.getName(), currentFile.getFileType().getName());
            VirtualFile[] filesInProject = findFilesInProject(alternateFileNames, currentProject, currentModule);
            openFiles(filesInProject, currentProject);
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    private String[] getAlternateNames(String currentFileName, String fileType) throws URISyntaxException
    {
        JRubyRunner runner = new JRubyRunner(AlternateApp.getSourceFileURI(), AlternateApp.getRubyClassName());
        IRubyObject alternateNames = runner.send("alternate_for", new IRubyObject[]{runner.newString(currentFileName), runner.newString(fileType)});
        return runner.rubyObjectAsStringArray(alternateNames);
    }

    private VirtualFile[] findFilesInProject(String[] fileNames, Project project, Module module)
    {
        ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();

        FindAndOpenFileContentIterator fileFinder = new FindAndOpenFileContentIterator(Arrays.asList(fileNames), module, projectFileIndex);
        return fileFinder.getMatchingFiles();
    }

}
