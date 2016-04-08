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
public class DigitalOutputSettings extends ArduinoComponentSettings {

    private double period;

    public void setPeriod(double period) {
        this.period = period;
        super.setPanel(getMyPanel());
    }

    public double getPeriod() {
        return period;
    }

    public DigitalOutputSettings(ArduinoManager arduinoManager) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.OUTPUT);
        super.setPanel(getMyPanel());
    }

    public void parseSettingsGUI(JPanel panel) {

        try {
            setPin(((Byte)(((JComboBox)(panel.getComponent(1))).getSelectedItem())).intValue());
            period = Double.parseDouble(((JTextField) panel.getComponent(3)).getText());
        } catch (NumberFormatException e){
            period = 0;
            //LOG
            System.out.println("Nepodporovany format periody");
        }

    }

    private JPanel getMyPanel(){
        JPanel myPanel = new JPanel(new GridLayout(0, 2));

        //PIN
        JComboBox pinComboBox = new JComboBox();
        JLabel pinLabel = new JLabel("Pin: ");
        Object[] modeArray = arduinoManager.getPinMap().get(getType()).toArray();
        Arrays.sort(modeArray);
        pinComboBox.setModel(new DefaultComboBoxModel(modeArray));
        // * if node already has arduino component - load from clickedNode
        if (getPin() != null) {
            pinComboBox.setSelectedItem(getPin().byteValue());
        } else {
            pinComboBox.setSelectedIndex(0);
        }
        myPanel.add(pinLabel); //0
        myPanel.add(pinComboBox); //1

        myPanel.add(new JLabel("Perioda: ", SwingConstants.LEFT)); //2
        myPanel.add(new JTextField(((Double)period).toString()));  //3

        myPanel.add(new JLabel("Tralalalala: ", SwingConstants.LEFT)); //4
        myPanel.add(new JTextField(((Double)period).toString()));      //5

        return myPanel;
    }

}
