package org.pneditor.arduino.components.common;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.AnalogInputSettings;
import org.pneditor.arduino.components.DigitalInputSettings;
import org.pneditor.arduino.components.DigitalOutputSettings;
import org.pneditor.arduino.components.SendMessageSettings;
import org.pneditor.petrinet.Node;

import javax.swing.*;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class ArduinoComponentSettings {

    protected JPanel panel;
    protected ArduinoComponentType type;
    protected Integer pin;
    protected ArduinoManager arduinoManager;

    protected ArduinoComponentSettings(ArduinoManager arduinoManager) {
        this.arduinoManager = arduinoManager;
    }

    public static ArduinoComponentSettings settingsFactory(ArduinoManager arduinoManager, ArduinoComponentType type, Node node) {
        //ADD ARDUINO COMPONENT
        switch (type) {
            case OUTPUT:
                return new DigitalOutputSettings(arduinoManager);
            case INPUT:
                return new DigitalInputSettings(arduinoManager);
            case ANALOG:
                return new AnalogInputSettings(arduinoManager);
            case MESSAGE:
                return new SendMessageSettings(arduinoManager);
            default:
                return new DigitalOutputSettings(arduinoManager);
        }
    }


    //COMMON METHODS
    public void parseSettingsGUI(JPanel panel){}

    //GETTER & SETTER
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public ArduinoComponentType getType() {
        return type;
    }

    public void setType(ArduinoComponentType type) {
        this.type = type;
    }

    public JPanel getSettingsGui() {
        return panel;
    }


}
