package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.openapi.vfs.VirtualFile;
import com.leapingfrogs.idea.mocks.MockFileHandler;
import com.leapingfrogs.idea.mocks.MockVirtualFile;
import junit.framework.TestCase;

import javax.swing.*;
import java.util.Arrays;

public class TestRunnableFileHandler extends TestCase {
    private RunnableFileHandler testee;

    private MockFileHandler fileHandler;
    private VirtualFile[] expectedFiles;

    protected void setUp() throws Exception {
        expectedFiles = new VirtualFile[]{new MockVirtualFile(), new MockVirtualFile()};

        JList valueList = mockJList(expectedFiles);

        fileHandler = new MockFileHandler();
        testee = new RunnableFileHandler(valueList, fileHandler);
    }

    public void testRun() {
        testee.run();
        assertTrue(Arrays.equals(expectedFiles, fileHandler.getOpenedFiles()));
    }

    private JList mockJList(final VirtualFile[] expectedFiles) {
        return new JList() {

            public Object[] getSelectedValues() {
                return expectedFiles;
            }
        };
    }

}
