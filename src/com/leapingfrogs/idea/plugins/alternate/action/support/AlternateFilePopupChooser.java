package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

public class AlternateFilePopupChooser {
    private Project currentProject;
    private FileHandler fileHandler;

    public AlternateFilePopupChooser(Project currentProject, FileHandler fileHandler) {
        this.currentProject = currentProject;
        this.fileHandler = fileHandler;
    }

    public void promptForFiles(VirtualFile[] files) {
        JList valueList = new JList(files);

        Runnable selectedFileHandler = new RunnableFileHandler(valueList, fileHandler);

        valueList.setCellRenderer(new AlternateFileListCellRenderer(currentProject));

        JBPopupFactory popupFactory = JBPopupFactory.getInstance();
        PopupChooserBuilder listPopupBuilder = popupFactory.createListPopupBuilder(valueList);
        listPopupBuilder.setItemChoosenCallback(selectedFileHandler);
        listPopupBuilder.setTitle("Select the file(s) to open");
        JBPopup jbPopup = listPopupBuilder.createPopup();
        jbPopup.showCenteredInCurrentWindow(currentProject);
    }

}
