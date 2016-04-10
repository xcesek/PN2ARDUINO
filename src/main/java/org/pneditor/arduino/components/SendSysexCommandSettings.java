package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;

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

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        command = (CustomSysexCommand) ((JComboBox) panel.getComponent(1)).getSelectedItem();
    }

    //GETTER & SETTER
    public CustomSysexCommand getCommand() {
        return command;
    }

    public void setCommand(CustomSysexCommand command) {
        this.command = command;
        super.setPanel(getMyPanel());
        //CustomSysexCommand.PID_TURN;
    }


    public enum CustomSysexCommand {

        PID_TURN((byte) 0x00) {
            @Override
            public String toString() {
                return "PID turn";
            }
        },
        PID_TURN2((byte) 0x46);

        private byte commandIdentifier;

//        PID_TURN{
//            @Override
//            public String toString() {
//                return "PID turn";
//            }
//        },

        CustomSysexCommand(byte commandIdentifier) {
            this.commandIdentifier = commandIdentifier;
        }

        public byte getCommandIdentifier() {
            return commandIdentifier;
        }
    }


}
