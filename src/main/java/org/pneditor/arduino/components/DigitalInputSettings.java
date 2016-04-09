package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class DigitalInputSettings extends ArduinoComponentSettings {

    public DigitalInputSettings(ArduinoManager arduinoManager, Integer pin) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.INPUT);
        super.setPanel(getMyPanel());
    }

    private JPanel getMyPanel() {
        JPanel myPanel = new JPanel(new GridLayout(0, 2));

        //PIN
        JComboBox pinComboBox = new JComboBox(arduinoManager.getUnusedPins(type));
        // * if node already has arduino component - load from clickedNode
        if (getPin() != null) {
            pinComboBox.setSelectedItem(getPin().byteValue());
        } else {
            pinComboBox.setSelectedIndex(0);
        }
        myPanel.add(new JLabel("Pin: ")); //0
        myPanel.add(pinComboBox); //1

        myPanel.add(Box.createVerticalStrut(5));
        myPanel.add(Box.createVerticalStrut(5));

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        pin = (((Byte) (((JComboBox) (panel.getComponent(1))).getSelectedItem())).intValue());
    }

}
