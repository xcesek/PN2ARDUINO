package org.pneditor.arduino.components.settings;

import org.pneditor.arduino.components.ArduinoComponentType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
public class DigitalOutputSettings extends ArduinoComponentSettings {

    private double period;

    public DigitalOutputSettings() {
        super.setType(ArduinoComponentType.OUTPUT);

        GridLayout customSettingsLayout = new GridLayout(0, 2);
        JPanel myPanel = new JPanel(customSettingsLayout);

        myPanel.add(new JLabel("Perioda: ", SwingConstants.LEFT));
        myPanel.add(new JTextField());

        super.setPanel(myPanel);
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

}
