package org.pneditor.arduino.components.common;

import org.firmata4j.Pin;
import org.pneditor.arduino.ArduinoManager;

import org.pneditor.arduino.components.DigitalOutput;
import org.pneditor.arduino.components.SendMessage;
import org.pneditor.petrinet.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArduinoComponentType type;
    protected ArduinoComponentSettings settings;
    protected ArduinoManager arduinoManager;
    public Color color;


    public ArduinoComponent(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager) {
        this.type = type;
        this.settings = settings;
        this.arduinoManager = arduinoManager;
    }

    public ArduinoComponentType getType() {
        return type;
    }

    public ArduinoComponentSettings getSettings() {
        return settings;
    }

    public void setType(ArduinoComponentType type) {
        this.type = type;
    }

    public void performAction(){}

    public void activate(){}

    public void deactivate(){}

    public void fire(){}

    public static ArduinoComponent componentFactory(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node){
        // tu doplnit
        switch (type) {
            case OUTPUT:
                    return new DigitalOutput(type, settings, arduinoManager);
            case MESSAGE:
                return new SendMessage(type, settings, arduinoManager);
            default:
                return new DigitalOutput(type, settings, arduinoManager);
        }
    }


}
