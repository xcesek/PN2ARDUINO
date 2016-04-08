package org.pneditor.arduino.components;

import org.firmata4j.Pin;
import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;

import java.awt.*;
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
public class DigitalOutput extends ArduinoComponent {

    private Pin myPin;

    public DigitalOutput(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager) {
        super(type, settings, arduinoManager);
        color = Color.CYAN;
        try {
            myPin = arduinoManager.getDevice().getPin(settings.getPin());
            myPin.setMode(Pin.Mode.OUTPUT);
        } catch (IOException e) {
            e.printStackTrace();
            //LOG
            System.out.println("!!! Pin " + settings.getPin() + " was not set!");
        }
        //LOG
        System.out.println("Pin " + settings.getPin() + " was set to output mode.");
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
