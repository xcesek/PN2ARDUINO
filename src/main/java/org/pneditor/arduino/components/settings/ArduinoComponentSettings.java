package org.pneditor.arduino.components.settings;

import org.pneditor.arduino.components.ArduinoComponentType;
import org.pneditor.arduino.components.DigitalOutput;

import javax.swing.*;
import java.awt.*;
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
abstract public class ArduinoComponentSettings {

    private JPanel panel;
    private ArduinoComponentType type;

    public JPanel getSettingsGui() {
        return panel;
    }

    public ArduinoComponentType getType(){
        return type;
    }

    public static ArduinoComponentSettings SettingsFactory(ArduinoComponentType type) {
        switch (type) {
            case INPUT:
                return new DigitalOutputSettings();
            default:
                return new DigitalOutputSettings();
        }
    }

    public void parseSettingsGUI(JPanel panel) {
    }

   //GETTER & SETTER


    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setType(ArduinoComponentType type) {
        this.type = type;
    }
}
