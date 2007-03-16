package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.openapi.vfs.VirtualFile;

public interface FileHandler
{
    void processFile(VirtualFile virtualFile);
}
