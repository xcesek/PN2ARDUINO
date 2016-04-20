package org.pneditor.arduino.components;

import org.firmata4j.Pin;
import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;
import org.pneditor.editor.RootPflow;
import org.pneditor.petrinet.Node;
import org.pneditor.util.LogEditor;

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

    public DigitalOutput(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        super(type, settings, arduinoManager, node);
        color = new Color(141, 224, 207);
        try {
            myPin = arduinoManager.getDevice().getPin(settings.getPin());
            myPin.setMode(Pin.Mode.OUTPUT);
        } catch (IOException e) {
            e.printStackTrace();
            PNEditor.getRoot().getLogEditor().log("ERROR: Pin " + settings.getPin() + " was not set to digital output mode!", LogEditor.logType.ARDUINO);
        }
        PNEditor.getRoot().getLogEditor().log("Pin " + settings.getPin() + " was set to digital output mode.", LogEditor.logType.ARDUINO);
    }

    @Override
    public void freeResources(){
        int index = arduinoManager.getUsedPins().indexOf(getSettings().getPin().byteValue());
        if(index != -1) {
            arduinoManager.getUsedPins().remove(index);
        }
    }

    @Override
    public void activate() {
        PNEditor.getRoot().getLogEditor().log("Activating: " + settings.getPin(), LogEditor.logType.ARDUINO);
        try {
            myPin.setValue(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate() {
        try {
            myPin.setValue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fire() {
        PNEditor.getRoot().getLogEditor().log("There is no logic on firing digital output without delay: " + settings.getPin(), LogEditor.logType.ARDUINO);
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
