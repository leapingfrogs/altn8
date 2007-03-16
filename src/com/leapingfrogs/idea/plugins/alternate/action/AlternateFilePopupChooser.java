package com.leapingfrogs.idea.plugins.alternate.action;

import com.leapingfrogs.idea.plugins.alternate.action.support.AlternateFileListCellRenderer;
import com.leapingfrogs.idea.plugins.alternate.action.support.FileHandler;
import com.intellij.ui.ListPopup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.*;

public class AlternateFilePopupChooser
{
    private Project currentProject;
    private FileHandler fileHandler;

    public AlternateFilePopupChooser(Project currentProject, FileHandler fileHandler)
    {
        this.currentProject = currentProject;
        this.fileHandler = fileHandler;
    }

    public void promptForFiles(VirtualFile[] files)
    {
        JList valueList = new JList(files);

        Runnable selectedFileHandler = new RunnableFileHandler(valueList, fileHandler);

        valueList.setCellRenderer(new AlternateFileListCellRenderer(currentProject));
        ListPopup popup = new ListPopup("Select Alternate File:", valueList, selectedFileHandler, currentProject);

        Dimension size = getPopupSize(popup);

        Container container = getRootIDEWindow(popup.getWindow());
        Dimension ideSize = container.getSize();
        Point location = container.getLocation();

        int x = (int) (location.getX() + (ideSize.getWidth() - size.getWidth()) / 2);
        int y = (int) (location.getY() + (ideSize.getHeight() - size.getHeight()) / 2);
        popup.show(x, y);
    }

    private Dimension getPopupSize(ListPopup pop)
    {
        pop.getWindow().pack();
        Dimension size = pop.getSize();
        return size;
    }

    private Container getRootIDEWindow(Container container)
    {
        if (container.getParent() != null)
        {
            return getRootIDEWindow(container.getParent());
        }
        return container;
    }

}
