package com.leapingfrogs.idea.plugins.alternate.action;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class AlternateConfiguration
{
    private JPanel rootComponent;
    private JTextField matchExpression;
    private JTextField replaceExpression;
    private JTable mappingTable;
    private JButton addMappingButton;
    private JButton removeMappingButton;
    private JButton upButton;
    private JButton downButton;
    private JScrollPane scrollPane;
    private final List mappings = new ArrayList();

    public AlternateConfiguration()
    {
        ButtonActionListener actionListener = new ButtonActionListener();

        addMappingButton.addActionListener( actionListener );

        removeMappingButton.addActionListener( actionListener );
        upButton.addActionListener( actionListener );
        downButton.addActionListener( actionListener );

        scrollPane.addMouseListener( new MouseListener()
        {
            public void mouseClicked( MouseEvent mouseEvent )
            {
                mappingTable.clearSelection();
            }

            public void mousePressed( MouseEvent mouseEvent )
            {
            }

            public void mouseReleased( MouseEvent mouseEvent )
            {
            }

            public void mouseEntered( MouseEvent mouseEvent )
            {
            }

            public void mouseExited( MouseEvent mouseEvent )
            {
            }
        } );
    }

    public JPanel getRootComponent()
    {
        return rootComponent;
    }

    public void setData( AlternateApplicationComponent data )
    {
        mappings.addAll( data.getMappings() );

        mappingTable.revalidate();
        mappingTable.repaint();
    }

    public void getData( AlternateApplicationComponent data )
    {
        List dataMappings = data.getMappings();
        dataMappings.clear();
        dataMappings.addAll( mappings );
    }

    public boolean isModified( AlternateApplicationComponent data )
    {
        return !mappings.equals( data.getMappings() );
    }

    private void createUIComponents()
    {
        mappingTable = new JTable( createTableModel() );
        mappingTable.setRowSelectionAllowed( true );
        mappingTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        mappingTable.getColumnModel().setColumnSelectionAllowed( false );
        mappingTable.setFocusable( false );
        mappingTable.getSelectionModel().addListSelectionListener( new ListSelectionListener()
        {
            public void valueChanged( ListSelectionEvent listSelectionEvent )
            {
                int selectedRowIndex = mappingTable.getSelectedRow();
                if ( selectedRowIndex != -1 )
                {
                    AlternateMapping selectedMapping = (AlternateMapping) mappings.get( selectedRowIndex );
                    matchExpression.setText( selectedMapping.getMatchExpression() );
                    replaceExpression.setText( selectedMapping.getReplaceExpression() );
                    addMappingButton.setText( "Update" );
                }
                else
                {
                    matchExpression.setText( "" );
                    replaceExpression.setText( "" );
                    addMappingButton.setText( "Add" );
                }
            }
        } );
        mappingTable.addMouseListener( new MouseListener()
        {
            public void mouseClicked( MouseEvent mouseEvent )
            {
                int selectedRow = mappingTable.getSelectedRow();
                if ( selectedRow != -1 )
                {
                    mappingTable.clearSelection();
                    mappingTable.getSelectionModel().setSelectionInterval( selectedRow, selectedRow );
                }
            }

            public void mousePressed( MouseEvent mouseEvent )
            {
            }

            public void mouseReleased( MouseEvent mouseEvent )
            {
            }

            public void mouseEntered( MouseEvent mouseEvent )
            {
            }

            public void mouseExited( MouseEvent mouseEvent )
            {
            }
        } );
    }

    private TableModel createTableModel()
    {
        return new AbstractTableModel()
        {
            public int getRowCount()
            {
                return mappings.size();
            }

            public int getColumnCount()
            {
                return 2;
            }

            public Object getValueAt( int row, int column )
            {
                AlternateMapping mapping = (AlternateMapping) mappings.get( row );
                switch ( column )
                {
                    case AlternateMapping.MATCH_EXPRESSION:
                        return mapping.getMatchExpression();
                    case AlternateMapping.REPLACE_EXPRESSION:
                        return mapping.getReplaceExpression();
                }
                throw new IllegalArgumentException( "Unknown column index: " + column );
            }

            public String getColumnName( int column )
            {
                switch ( column )
                {
                    case AlternateMapping.MATCH_EXPRESSION:
                        return "Match Expression";
                    case AlternateMapping.REPLACE_EXPRESSION:
                        return "Replace Expression";
                }
                throw new IllegalArgumentException( "Unknown column index: " + column );
            }

            public boolean isCellEditable( int i, int i1 )
            {
                return false;
            }
        };
    }

    private class ButtonActionListener implements ActionListener
    {
        public void actionPerformed( ActionEvent actionEvent )
        {
            String command = actionEvent.getActionCommand();

            if ( matchExpression.getText() != null && replaceExpression.getText() != null )
            {
                AlternateMapping selectedMapping = new AlternateMapping( matchExpression.getText(), replaceExpression.getText() );

                if ( command.equals( "Add" ) )
                {
                    performAddMapping( selectedMapping );
                    return;
                }
                if ( command.equals( "Update" ) )
                {
                    performUpdateMapping( selectedMapping );
                    return;
                }
                if ( command.equals( "Remove" ) )
                {
                    performRemoveMapping( selectedMapping );
                    return;
                }
                if ( command.equals( "Up" ) )
                {
                    performMoveMapping( selectedMapping, true );
                    return;
                }
                if ( command.equals( "Down" ) )
                {
                    performMoveMapping( selectedMapping, false );
                    return;
                }

                System.out.println( "Unknown Action: " + command );
            }
        }

        private void performUpdateMapping( AlternateMapping selectedMapping )
        {
            int selectedRow = mappingTable.getSelectedRow();

            mappings.remove( selectedRow );
            mappings.add( selectedRow, selectedMapping );
            setSelectedMapping( selectedMapping );
            updateTable();
        }

        private void performMoveMapping( AlternateMapping selectedMapping, boolean moveUp )
        {
            int selectedRow = mappingTable.getSelectedRow();
            int newPosition = selectedRow + ( moveUp ? -1 : 1 );

            if ( rowInRange( newPosition ) )
            {
                mappings.remove( selectedMapping );
                mappings.add( newPosition, selectedMapping );
                setSelectedMapping( selectedMapping );
                updateTable();
            }
        }

        private void performRemoveMapping( AlternateMapping selectedMapping )
        {
            if ( mappings.contains( selectedMapping ) )
            {
                int nextIndex = mappings.indexOf( selectedMapping );
                AlternateMapping nextMapping = null;
                if ( nextIndex < mappings.size() - 1 )
                {
                    nextMapping = (AlternateMapping) mappings.get( nextIndex + 1 );
                }
                else
                {
                    if ( mappings.size() > 1 )
                    {
                        nextMapping = (AlternateMapping) mappings.get( nextIndex - 1 );
                    }
                }

                mappings.remove( selectedMapping );
                setSelectedMapping( nextMapping );
                updateTable();
                clearTextFields();
            }
        }

        private void performAddMapping( AlternateMapping selectedMapping )
        {
            if ( !mappings.contains( selectedMapping ) )
            {
                mappings.add( selectedMapping );
                setSelectedMapping( selectedMapping );
                updateTable();
                clearTextFields();
            }
        }

        private void setSelectedMapping( AlternateMapping selectedMapping )
        {
            mappingTable.getSelectionModel().clearSelection();
            if ( selectedMapping != null )
            {
                int selectedRow = mappings.indexOf( selectedMapping );
                if ( rowInRange( selectedRow ) )
                {
                    mappingTable.getSelectionModel().setSelectionInterval( selectedRow, selectedRow );
                }
            }
        }

        private boolean rowInRange( int row )
        {
            return row >= 0 && row < mappingTable.getModel().getRowCount();
        }

        private void clearTextFields()
        {
            matchExpression.setText( "" );
            replaceExpression.setText( "" );
        }

        private void updateTable()
        {
            mappingTable.revalidate();
            mappingTable.repaint();
        }
    }
}
