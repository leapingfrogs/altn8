package com.leapingfrogs.idea.plugins.alternate.action.support;

import com.intellij.ide.IconUtilEx;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.FileStatusManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.*;

class AlternateFileListCellRenderer extends ColoredListCellRenderer
{
    private final Project project;

    public AlternateFileListCellRenderer( Project currentProject )
    {
        this.project = currentProject;
    }

    protected void customizeCellRenderer( JList list, Object value, int index, boolean selected, boolean hasFocus )
    {
        if ( value instanceof VirtualFile )
        {
            VirtualFile virtualfile = (VirtualFile) value;
            String fileName = virtualfile.getName();
            setIcon( IconUtilEx.getIcon( virtualfile, 2, project ) );
            FileStatus filestatus = FileStatusManager.getInstance( project ).getStatus( virtualfile );
            TextAttributes textattributes = new TextAttributes( filestatus.getColor(), null, null, EffectType.LINE_UNDERSCORE, 0 );
            append( fileName, SimpleTextAttributes.fromTextAttributes( textattributes ) );
        }
    }
}
