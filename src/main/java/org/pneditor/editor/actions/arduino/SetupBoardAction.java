/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.pneditor.editor.actions.arduino;

import jssc.SerialPortList;
import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.BoardSettings;
import org.pneditor.editor.Root;
import org.pneditor.editor.RootPflow;
import org.pneditor.editor.canvas.*;
import org.pneditor.editor.canvas.Canvas;
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

    private Cursor oldCursor;
    private Canvas canvas;

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

            String port = requestPort();
            if(!port.equals("")) {
                boardSettings.setPort(port);
                ((RootPflow)root).getActivateArduino().setEnabled(true);
                ((RootPflow)root).getActivateArduino().actionPerformed(e);

                canvas.activeCursor = oldCursor;
                canvas.setCursor(canvas.activeCursor);
            }
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

                canvas = ((RootPflow) root).getCanvas();
                oldCursor = canvas.getCursor();
                canvas.activeCursor = GraphicsTools.getCursor("pneditor/arduino/wait.png", new Point(16, 16));
                canvas.setCursor(canvas.activeCursor);
                root.repaintCanvas();

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
