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
    }

    //GETTER & SETTER
    public CustomSysexCommand getCommand() {
        return command;
    }

    public void setCommand(CustomSysexCommand command) {
        this.command = command;
        super.setPanel(getMyPanel());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        super.setPanel(getMyPanel());
    }



    public enum CustomSysexCommand {

        CUSTOM_0((byte) 0x00) {
            @Override
            public String toString() {
                return "0x00";
            }
        },
        CUSTOM_1((byte) 0x01) {
            @Override
            public String toString() {
                return "0x01";
            }
        },
        CUSTOM_2((byte) 0x02) {
            @Override
            public String toString() {
                return "0x02";
            }
        },
        CUSTOM_3((byte) 0x03) {
            @Override
            public String toString() {
                return "0x03";
            }
        },
        CUSTOM_4((byte) 0x04) {
            @Override
            public String toString() {
                return "0x04";
            }
        },
        CUSTOM_5((byte) 0x05) {
            @Override
            public String toString() {
                return "0x05";
            }
        },
        CUSTOM_6((byte) 0x06) {
            @Override
            public String toString() {
                return "0x06";
            }
        },
        CUSTOM_7((byte) 0x07) {
            @Override
            public String toString() {
                return "0x07";
            }
        },
        CUSTOM_8((byte) 0x08) {
            @Override
            public String toString() {
                return "0x08";
            }
        },
        CUSTOM_9((byte) 0x09) {
            @Override
            public String toString() {
                return "0x09";
            }
        },
        CUSTOM_10((byte) 0x0A) {
            @Override
            public String toString() {
                return "0x0A";
            }
        },
        CUSTOM_11((byte) 0x0B) {
            @Override
            public String toString() {
                return "0x0B";
            }
        },
        CUSTOM_12((byte) 0x0C) {
            @Override
            public String toString() {
                return "0x0C";
            }
        },
        CUSTOM_13((byte) 0x0D) {
            @Override
            public String toString() {
                return "0x0D";
            }
        },
        CUSTOM_14((byte) 0x0E) {
            @Override
            public String toString() {
                return "0x0E";
            }
        },
        CUSTOM_15((byte) 0x0F) {
            @Override
            public String toString() {
                return "0x0F";
            }
        };

        private byte commandIdentifier;

        CustomSysexCommand(byte commandIdentifier) {
            this.commandIdentifier = commandIdentifier;
        }

        public byte getCommandIdentifier() {
            return commandIdentifier;
        }


    }
}
