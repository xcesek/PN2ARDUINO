package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.arduino.components.common.CustomSysexCommand;
import org.pneditor.editor.PNEditor;
import org.pneditor.util.LogEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class SendSysexCommandSettings extends ArduinoComponentSettings {

    private CustomSysexCommand command;
    private String message;

    public SendSysexCommandSettings(ArduinoManager arduinoManager) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.CUSTOM_SYSEX);
        super.setPanel(getMyPanel());
    }

    private JPanel getMyPanel() {
        GridLayout customSettingsLayout = new GridLayout(0, 2);
        JPanel myPanel = new JPanel(customSettingsLayout);

        JComboBox commandComboBox = new JComboBox(CustomSysexCommand.values());
        if (command != null) {
            commandComboBox.setSelectedItem(command);
        } else {
            commandComboBox.setSelectedIndex(0);
        }

        myPanel.add(new JLabel("Command: ", SwingConstants.LEFT)); //0
        myPanel.add(commandComboBox); //1

        myPanel.add(Box.createVerticalStrut(5)); //2
        myPanel.add(Box.createVerticalStrut(5)); //3

        myPanel.add(new JLabel("Message: ", SwingConstants.LEFT)); //4
        myPanel.add(new JTextField(message)); //5

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        command = (CustomSysexCommand) ((JComboBox) panel.getComponent(1)).getSelectedItem();
        message = ((JTextField) panel.getComponent(5)).getText();
        if(message.length() > 14) {
            PNEditor.getRoot().getLogEditor().log("Your message is longer then 14 characters, it would by trimmed.", LogEditor.logType.ARDUINO);
            message = message.substring(0,13);
        }
    }

    @Override
    public void actualizeSettingsGUI() {
        super.setPanel(getMyPanel());
    }

    //GETTER & SETTER
    public CustomSysexCommand getCommand() {
        return command;
    }

    public void setCommand(CustomSysexCommand command) {
        this.command = command;
        actualizeSettingsGUI();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        actualizeSettingsGUI();
    }


}
