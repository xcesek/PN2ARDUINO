package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.settings.ArduinoComponentSettings;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class ArduinoComponent {
    private int pin;
    private ArduinoComponentType type;
    private ArduinoComponentSettings settings;
    private ArduinoManager arduinoManager;

    public ArduinoComponent(int pin, ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager) {
        this.pin = pin;
        this.type = type;
        this.settings = settings;
        this.arduinoManager = arduinoManager;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public ArduinoComponentType getType() {
        return type;
    }

    public void setType(ArduinoComponentType type) {
        this.type = type;
    }

    public void performAction(){}

    public static ArduinoComponent componentFactory(int pin, ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager){
        switch (type) {
            case OUTPUT:
                return new DigitalOutput(pin, type, settings, arduinoManager);
            default:
                return new ArduinoComponent(pin, type, settings, arduinoManager);
        }
    }
}
