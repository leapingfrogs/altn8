package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.List;

public class FindAndOpenFileContentIterator implements ContentIterator
{
    private List fileNames;
    private Module module;
    private ProjectFileIndex fileIndex;

    private List<VirtualFile> possibleModuleFiles;
    private List<VirtualFile> possibleProjectFiles;

    public FindAndOpenFileContentIterator(List<String> alternateFileNames, Module currentModule, ProjectFileIndex projectFileIndex)
    {
        fileNames = alternateFileNames;
        module = currentModule;
        fileIndex = projectFileIndex;
        possibleModuleFiles = new ArrayList<VirtualFile>();
        possibleProjectFiles = new ArrayList<VirtualFile>();
    }

    public boolean processFile(VirtualFile fileOrDir)
    {
        if (!fileOrDir.isDirectory() && fileNames.contains(fileOrDir.getName()))
        {
            Module moduleForFile = fileIndex.getModuleForFile(fileOrDir);
            if (module.equals(moduleForFile))
            {
                possibleModuleFiles.add(fileOrDir);
            } else
            {
                possibleProjectFiles.add(fileOrDir);
            }
        }
        return true;
    }

    public VirtualFile[] getMatchingFiles()
    {
        fileIndex.iterateContent(this);
        return possibleModuleFiles.isEmpty() ? possibleProjectFiles.toArray(new VirtualFile[0]) : possibleModuleFiles.toArray(new VirtualFile[0]);
    }
}
