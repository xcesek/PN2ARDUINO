package org.pneditor.editor.actions.arduino;

import org.firmata4j.Pin;
import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.Root;
import org.pneditor.editor.commands.arduino.SetArduinoComponentCommand;
import org.pneditor.petrinet.Node;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class AddArduinoComponentAction extends AbstractAction {

    private Root root;
    //COMPONENT SETTINGS
    private ArduinoComponentType type;
    private ArduinoComponentSettings arduinoComponentSettings;

    private Node clickedNode;

    public AddArduinoComponentAction(Root root, ArduinoComponentType type) {
        this.root = root;
        this.type = type;
        String name = type.toString();
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/addArduinoComponent16.png"));
        putValue(SHORT_DESCRIPTION, name);
//		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
//		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("R"));
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            if (root.getClickedElement() != null
                    && root.getClickedElement() instanceof Node) {
                clickedNode = (Node) root.getClickedElement();

                if (requestComponentSettings()) {
                    //TODO prerobit cez comand
                    ArduinoComponent arduinoComponent = ArduinoComponent.componentFactory(type, arduinoComponentSettings, root.getArduinoManager(), clickedNode);
                    root.getUndoManager().executeCommand(new SetArduinoComponentCommand(clickedNode, arduinoComponent));
                }
            }
        }

        arduinoComponentSettings = null;
    }

    public boolean requestComponentSettings() {

        if (clickedNode.hasArduinoComponent() && clickedNode.getArduinoComponent().getType() == type) {
            arduinoComponentSettings = clickedNode.getArduinoComponent().getSettings();
        } else {
            arduinoComponentSettings = ArduinoComponentSettings.settingsFactory(root.getArduinoManager(), type, clickedNode);
        }
        JPanel customSettings = arduinoComponentSettings.getSettingsGui();

        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(customSettings);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

        JDialog dialog = optionPane.createDialog(root.getParentFrame(), type.toString());
        dialog.setVisible(true);
        dialog.setResizable(true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        //if OK
        int value = (optionPane.getValue() != null) ? ((Integer) optionPane.getValue()).intValue() : JOptionPane.CANCEL_OPTION;
        if (value == JOptionPane.YES_OPTION) {
            arduinoComponentSettings.parseSettingsGUI(customSettings);
            return true;
        }
        return false;
    }
}