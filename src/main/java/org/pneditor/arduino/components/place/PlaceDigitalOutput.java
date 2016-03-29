package org.pneditor.arduino.components.place;

import org.firmata4j.Pin;
import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.ArduinoComponent;
import org.pneditor.arduino.components.ArduinoComponentType;
import org.pneditor.arduino.components.ArduinoComponentSettings;

import java.io.IOException;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class PlaceDigitalOutput extends ArduinoComponent {

    private Pin myPin;

    public PlaceDigitalOutput(int pin, ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager) {
        super(pin, type, settings, arduinoManager);
        try {
            myPin = arduinoManager.getDevice().getPin(pin);
            myPin.setMode(Pin.Mode.OUTPUT);
        } catch (IOException e) {
            e.printStackTrace();
            //LOG
            System.out.println("!!! Pin " + pin + " was not set!");
        }
        //LOG
        System.out.println("Pin " + pin + " was set to output mode.");
    }

    public void performAction(){
        try {
            myPin.setValue(myPin.getValue() == 1 ? 0:1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void activate() {
        try {
            myPin.setValue(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deactivate() {
        try {
            myPin.setValue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
