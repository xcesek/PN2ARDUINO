package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
public class DigitalOutputSettings extends ArduinoComponentSettings {

    private double period;

    public DigitalOutputSettings(ArduinoManager arduinoManager, Integer pin) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.OUTPUT);
        super.setPanel(getMyPanel());
        if(pin != null) {
            arduinoManager.getUsedPins().add(pin.byteValue());
        }
        super.pin = pin;
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

        myPanel.add(new JLabel("Perioda: ", SwingConstants.LEFT)); //2
        myPanel.add(new JTextField(((Double) period).toString()));  //3

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        try {
            pin = (((Byte) (((JComboBox) (panel.getComponent(1))).getSelectedItem())).intValue());
            period = 0;
            //period = Double.parseDouble(((JTextField) panel.getComponent(3)).getText());
            //Mark pin as used
            arduinoManager.getUsedPins().add(pin.byteValue());
        } catch (NumberFormatException e) {
            period = 0;
            //LOG
            System.out.println("Nepodporovany format periody");
        }
    }

    //GETTER & SETTER
    public void setPeriod(double period) {
        this.period = period;
        super.setPanel(getMyPanel());
    }

    public double getPeriod() {
        return period;
    }

}
