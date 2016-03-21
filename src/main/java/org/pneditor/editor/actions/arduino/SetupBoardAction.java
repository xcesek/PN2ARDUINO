/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pneditor.editor.actions.arduino;

import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.arduino.settings.BoardSettings;
import org.pneditor.arduino.settings.BoardType;
import org.pneditor.editor.Root;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


public class SetupBoardAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private JComboBox boardTypeCombo;
    private JTextField portField;
    private JCheckBox verboseOutput;
    private JCheckBox preserveTempFiles;

    private boolean alreadySetup;

    public SetupBoardAction(Root root) {
        this.root = root;
        String name = "Setup board";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/settings.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);
        alreadySetup = false;

        arduinoManager = root.getArduinoManager();

        boardTypeCombo = new JComboBox(BoardType.getBoardNames());
        portField = new JTextField(2);
        verboseOutput = new JCheckBox();
        preserveTempFiles = new JCheckBox();

    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            BoardSettings boardSettings = arduinoManager.getBoardSettings();

            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(popupContent(boardSettings));

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Arduino Board Setup");
            dialog.setVisible(true);

            int value = (optionPane.getValue() != null) ? ((Integer) optionPane.getValue()).intValue() : JOptionPane.CANCEL_OPTION;
            if (value == JOptionPane.YES_OPTION) {
                // set user values
                boardSettings.setPort(portField.getText());

                String boardTypeStr = (String) boardTypeCombo.getSelectedItem();
                if (boardTypeStr != null && !boardTypeStr.isEmpty()) {
                    boardSettings.setBoardType(BoardType.byName((String) boardTypeCombo.getSelectedItem()));
                } else {
                    boardSettings.setBoardType(BoardType.valueOf(BoardType.ARDUINO_UNO.getBoardName()));
                }

                boardSettings.setVerboseOutput(verboseOutput.isSelected());
                boardSettings.setPreserveTempFiles(preserveTempFiles.isSelected());

                alreadySetup = true;
                root.refreshAll();
            }

        }
    }

    private Object[] popupContent(final BoardSettings boardSettings) {

        GridLayout gridLayout = new GridLayout(0,2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(gridLayout);

        popupPanel.add(new JLabel("Arduino Board Type:"));
        boardTypeCombo.setSelectedItem(boardSettings.getBoardType() == null ? BoardType.ARDUINO_UNO.getBoardName() : boardSettings.getBoardType().getBoardName());
        popupPanel.add(boardTypeCombo);

        popupPanel.add(new JLabel("Port:"));
        portField.setText(boardSettings.getPort());
        popupPanel.add(portField);

        popupPanel.add(new JLabel("Verbose output:"));
        popupPanel.add(verboseOutput);
        verboseOutput.setSelected(boardSettings.isVerboseOutput());

        popupPanel.add(new JLabel("Preserve temp files:"));
        preserveTempFiles.setSelected(boardSettings.isPreserveTempFiles());
        popupPanel.add(preserveTempFiles);

        List<Object> objects = new ArrayList<>();
        objects.add(popupPanel);
        return objects.toArray();
    }

    public boolean isAlreadySetup() {
        return alreadySetup;
    }
}
