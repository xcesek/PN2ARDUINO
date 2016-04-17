package org.pneditor.arduino.components;

import org.firmata4j.Pin;
import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;
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
public class AnalogInput extends ArduinoComponent {

    private Pin myPin;

    public AnalogInput(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        super(type, settings, arduinoManager, node);
        color = Color.RED;
        try {
            myPin = arduinoManager.getDevice().getPin(settings.getPin());
            myPin.setMode(Pin.Mode.ANALOG);
        } catch (IOException e) {
            e.printStackTrace();
            PNEditor.getRoot().getLogEditor().log("ERROR: Pin " + settings.getPin() + " was not set to analog input mode!", LogEditor.logType.ARDUINO);
        }
        PNEditor.getRoot().getLogEditor().log("Pin " + settings.getPin() + " was set to analog input mode.", LogEditor.logType.ARDUINO);
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

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void fire() {

    }

    @Override
    public boolean isEnabled(){
        double bottomThreshold = ((AnalogInputSettings) settings).getBottomThreshold();
        double upThreshold = ((AnalogInputSettings) settings).getUpThreshold();
        if(myPin.getValue() >= bottomThreshold && myPin.getValue() <= upThreshold) {
            return true;
        } else {
            return false;
        }
    }
}
