package org.pneditor.arduino;

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

    public ArduinoComponent(int pin, ArduinoComponentType type) {
        this.pin = pin;
        this.type = type;
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
}
