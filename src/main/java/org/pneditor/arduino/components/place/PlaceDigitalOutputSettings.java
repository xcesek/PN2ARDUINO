package org.pneditor.arduino.components.place;

import org.pneditor.arduino.components.ArduinoComponentSettings;
import org.pneditor.arduino.components.ArduinoComponentType;

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
public class PlaceDigitalOutputSettings extends ArduinoComponentSettings {

    private double period;

    public void setPeriod(double period) {
        this.period = period;
        super.setPanel(getMyPanel());
    }

    public double getPeriod() {
        return period;
    }

    public PlaceDigitalOutputSettings() {
        super.setType(ArduinoComponentType.OUTPUT);
        super.setPanel(getMyPanel());
    }

    public void parseSettingsGUI(JPanel panel) {

        try {
            period = Double.parseDouble(((JTextField) panel.getComponent(1)).getText());
        } catch (NumberFormatException e){
            period = 0;
            //LOG
            System.out.println("Nepodporovany format periody");
        }

    }

    private JPanel getMyPanel(){
        GridLayout customSettingsLayout = new GridLayout(0, 2);
        JPanel myPanel = new JPanel(customSettingsLayout);

        myPanel.add(new JLabel("Perioda: ", SwingConstants.LEFT));
        myPanel.add(new JTextField(((Double)period).toString()));

        return myPanel;
    }

}
