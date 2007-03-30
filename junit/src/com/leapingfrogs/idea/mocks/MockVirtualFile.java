package com.leapingfrogs.idea.mocks;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockVirtualFile extends VirtualFile {
    public String getName() {
        return null;
    }

    public VirtualFileSystem getFileSystem() {
        return null;
    }

    public String getPath() {
        return null;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isDirectory() {
        return false;
    }

    public boolean isValid() {
        return false;
    }

    public VirtualFile getParent() {
        return null;
    }

    public VirtualFile[] getChildren() {
        return new VirtualFile[0];
    }

    public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
        return null;
    }

    public byte[] contentsToByteArray() throws IOException {
        return new byte[0];
    }

    public long getTimeStamp() {
        return 0;
    }

    public long getLength() {
        return 0;
    }

    public void refresh(boolean asynchronous, boolean recursive, Runnable postRunnable) {
    }

    public InputStream getInputStream() throws IOException {
        return null;
    }
}
