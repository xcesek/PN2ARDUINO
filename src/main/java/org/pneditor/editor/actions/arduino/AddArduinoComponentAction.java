package org.pneditor.editor.actions.arduino;

import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;
import org.pneditor.editor.Root;
import org.pneditor.editor.commands.arduino.SetArduinoComponentCommand;
import org.pneditor.petrinet.Node;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
    private Node clickedNode;
    //COMPONENT SETTINGS
    private ArduinoComponentType type;
    private ArduinoComponentSettings arduinoComponentSettings;
    private JPanel customSettings;

    public AddArduinoComponentAction(Root root, ArduinoComponentType type) {
        this.root = root;
        this.type = type;
        String name = type.toString();
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/addArduinoComponent16.png"));
        putValue(SHORT_DESCRIPTION, name);
        setComponentIcon(type);
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            if (root.getClickedElement() != null
                    && root.getClickedElement() instanceof Node) {
                clickedNode = (Node) root.getClickedElement();

                String option = requestComponentSettings();
                if (option.equals("Save")) {
                    arduinoComponentSettings.parseSettingsGUI(customSettings);
                    ArduinoComponent arduinoComponent = ArduinoComponent.componentFactory(type, arduinoComponentSettings, root.getArduinoManager(), clickedNode);
                    clickedNode.setArduinoComponent(arduinoComponent);
                }
                if (option.equals("Delete") && clickedNode.hasArduinoComponent() && clickedNode.getArduinoComponent().getType() == type) {
                    if (clickedNode.hasArduinoComponent() ) {
                        clickedNode.getArduinoComponent().freeResources();
                        clickedNode.setArduinoComponent(null);
                        PNEditor.getRoot().repaintCanvas();
                    }
                }
                if (option.equals("Cancel")) {
                    // do nothing
                }
            }
        }
        arduinoComponentSettings = null;

    }

    public String requestComponentSettings() {

        JPanel message = new JPanel();

        if (clickedNode.hasArduinoComponent() && clickedNode.getArduinoComponent().getType() == type) {
            arduinoComponentSettings = clickedNode.getArduinoComponent().getSettings();
        } else {
            arduinoComponentSettings = ArduinoComponentSettings.settingsFactory(root.getArduinoManager(), null, type, clickedNode);
        }
        customSettings = arduinoComponentSettings.getSettingsGui();

        message.add(customSettings);
        message.add(Box.createHorizontalStrut(10));
        message.add(arduinoComponentSettings.getInfoPanel());

        Object[] options = {"Save", "Delete", "Cancel"};

        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(message);
        optionPane.setOptions(options);
        optionPane.setInitialValue(options[0]);

        JDialog dialog = optionPane.createDialog(root.getParentFrame(), type.toString());
        dialog.setVisible(true);
        dialog.setResizable(true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        //if OK
        if (optionPane.getValue() != null) {
            return (String) optionPane.getValue();
        } else {
            return "Cancel";
        }
    }

    private void setComponentIcon(ArduinoComponentType type) {
        switch (type){
            case INPUT:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/140 167 188 digitalIn.png"));
                break;
            case OUTPUT:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/141 224 207 digitalOut.png"));
                break;
            case ANALOG:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/237 144 131 analogIn.png"));
                break;
            case SERVO:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/120 90 186 servo.png"));
                break;
            case MESSAGE:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/195 141 165 sendMessage.png"));
                break;
            case CUSTOM_SYSEX:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/198 110 220 sendSysexCommand.png"));
                break;
            case PWM:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/237 201 131 PWM.png"));
                break;
            default:
                putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/addArduinoComponent16.png"));
                break;
        }
    }
}