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

import org.pneditor.arduino.settings.BoardSettings;
import org.pneditor.arduino.settings.BoardType;
import org.pneditor.editor.Root;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class SetupBoardAction extends AbstractAction {

    private Root root;

    public SetupBoardAction(Root root) {
        this.root = root;
        String name = "Setup board";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/uploadCode.gif"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            BoardSettings boardSettings = root.getBoardSettings();

            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(popupContent(boardSettings));

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Arduino Board Setup");
            dialog.setVisible(true);

        }

    }

    private Object[] popupContent(final BoardSettings boardSettings) {
        Object[] objects = new Object[4];

        objects[0] = (new JLabel("Arduino Board Type:"));

        final JComboBox boardTypeCombo = new JComboBox(BoardType.getBoardNames());
        boardTypeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String boardTypeStr = (String) boardTypeCombo.getSelectedItem();
                if (boardTypeStr != null && !boardTypeStr.isEmpty()) {
                    boardSettings.setBoardType(BoardType.byName((String) boardTypeCombo.getSelectedItem()));
                } else {
                    boardSettings.setBoardType(BoardType.valueOf(BoardType.ARDUINO_UNO.getBoardName()));
                }
            }
        });
        boardTypeCombo.setSelectedItem(boardSettings.getBoardType() == null ? BoardType.ARDUINO_UNO.getBoardName() : boardSettings.getBoardType().getBoardName());
        objects[1] = boardTypeCombo;

        objects[2] = (new JLabel("Port:"));

        final JTextField portField = new JTextField(2);
        portField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                boardSettings.setPort(portField.getText());
            }
        });
        portField.setText(boardSettings.getPort());
        objects[3] = portField;


        return objects;
    }

}
