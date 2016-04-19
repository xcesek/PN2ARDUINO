package org.pneditor.arduino.components.common;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.*;
import org.pneditor.petrinet.Node;

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
public class ArduinoComponentSettings {

    protected JPanel panel;
    protected JPanel infoPanel;
    protected ArduinoComponentType type;
    protected Integer pin;
    protected ArduinoManager arduinoManager;

    protected ArduinoComponentSettings(ArduinoManager arduinoManager) {
        this.arduinoManager = arduinoManager;
    }

    public static ArduinoComponentSettings settingsFactory(ArduinoManager arduinoManager, Integer pin, ArduinoComponentType type, Node node) {
        //ADD ARDUINO COMPONENT
        switch (type) {
            case OUTPUT:
                return new DigitalOutputSettings(arduinoManager, pin);
            case INPUT:
                return new DigitalInputSettings(arduinoManager, pin);
            case ANALOG:
                return new AnalogInputSettings(arduinoManager, pin);
            case MESSAGE:
                return new SendMessageSettings(arduinoManager);
            case CUSTOM_SYSEX:
                return new SendSysexCommandSettings(arduinoManager);
            case PWM:
                return new PWMSettings(arduinoManager, pin, node);
            case SERVO:
                return new ServoSettings(arduinoManager, pin, node);
            default:
                return new DigitalOutputSettings(arduinoManager, pin);
        }
    }


    //COMMON METHODS
    public void parseSettingsGUI(JPanel panel){}

    public void actualizeSettingsGUI() {}

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
        actualizeSettingsGUI();
        return panel;
    }

    public JPanel getInfoPanel(){
        return infoPanel;
    }
    public void setInfoPanel(JPanel panel) {
        infoPanel = panel;
    }


}
