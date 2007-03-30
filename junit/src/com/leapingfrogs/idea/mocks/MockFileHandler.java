package com.leapingfrogs.idea.mocks;

import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.plugins.alternate.action.support.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class MockFileHandler implements FileHandler {
    private List openedFiles = new ArrayList();

    public void processFile(VirtualFile virtualFile) {
        openedFiles.add(virtualFile);
    }

    public VirtualFile[] getOpenedFiles() {
        return (VirtualFile[]) openedFiles.toArray(new VirtualFile[0]);
    }
}
