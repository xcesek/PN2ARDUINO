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

import jssc.SerialPortList;
import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.BoardSettings;
import org.pneditor.editor.Root;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class SetupBoardAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private JTextField portField;

    private boolean alreadySetup;

    public SetupBoardAction(Root root) {
        this.root = root;
        String name = "Setup board";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/setupBoard16.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);
        alreadySetup = false;

        arduinoManager = root.getArduinoManager();

        portField = new JTextField(2);

    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            BoardSettings boardSettings = arduinoManager.getBoardSettings();

            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(popupContent(boardSettings));

            boardSettings.setPort(requestPort());
            root.refreshAll();
        }
    }

    private Object[] popupContent(final BoardSettings boardSettings) {
        ArrayList<Object> objectList = new ArrayList<Object>();

        objectList.add(new JLabel("Port:"));
        portField.setText(boardSettings.getPort());
        objectList.add(portField);

        return objectList.toArray();
    }

    private String requestPort() {
        // combobox with available serial ports
        JComboBox<String> portNameSelector = new JComboBox<>();
        portNameSelector.setModel(new DefaultComboBoxModel<String>());
        String[] portNames = SerialPortList.getPortNames();
        for (String portName : portNames) {
            portNameSelector.addItem(portName);
        }
        // if there is no serial port
        if (portNameSelector.getItemCount() == 0) {
            JOptionPane.showMessageDialog(root.getParentFrame(), "Cannot find any Arduino on serial port", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        } else {
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.add(new JLabel("Port "));
            panel.add(portNameSelector);
            if (JOptionPane.showConfirmDialog(root.getParentFrame(), panel, "Select the port", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                alreadySetup = true;
                return portNameSelector.getSelectedItem().toString();
            } else {
                return "";
            }
        }

    }


    public boolean isAlreadySetup() {
        return alreadySetup;
    }
}
