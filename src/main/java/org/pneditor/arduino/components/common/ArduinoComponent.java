package org.pneditor.arduino.components.common;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.*;
import org.pneditor.petrinet.Node;

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
public class ArduinoComponent {

    protected ArduinoComponentType type;
    protected ArduinoComponentSettings settings;
    protected ArduinoManager arduinoManager;
    protected Node node;

    public Color color;

    public ArduinoComponent(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        this.type = type;
        this.settings = settings;
        this.arduinoManager = arduinoManager;
        this.node = node;
    }

    public static ArduinoComponent componentFactory(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        //ADD ARDUINO COMPONENT
        switch (type) {
            case OUTPUT:
                return new DigitalOutput(type, settings, arduinoManager, node);
            case INPUT:
                return new DigitalInput(type, settings, arduinoManager, node);
            case ANALOG:
                return new AnalogInput(type, settings, arduinoManager, node);
            case MESSAGE:
                return new SendMessage(type, settings, arduinoManager, node);
            case CUSTOM_SYSEX:
                return new SendSysexCommand(type, settings, arduinoManager, node);
            default:
                return new DigitalOutput(type, settings, arduinoManager, node);
        }
    }

    //COMMON METHODS
    public void activate() {
    }

    public void deactivate() {
    }

    public void fire() {
    }

    public boolean isEnabled(){
        return true;
    }

    //GETTER & SETTER
    public ArduinoComponentType getType() {
        return type;
    }

    public void setType(ArduinoComponentType type) {
        this.type = type;
    }

    public ArduinoComponentSettings getSettings() {
        return settings;
    }


}
