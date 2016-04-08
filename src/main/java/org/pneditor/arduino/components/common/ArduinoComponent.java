package org.pneditor.arduino.components.common;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.AnalogInput;
import org.pneditor.arduino.components.DigitalInput;
import org.pneditor.arduino.components.DigitalOutput;
import org.pneditor.arduino.components.SendMessage;
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

    public Color color;

    public ArduinoComponent(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager) {
        this.type = type;
        this.settings = settings;
        this.arduinoManager = arduinoManager;
    }

    public static ArduinoComponent componentFactory(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        //ADD ARDUINO COMPONENT
        switch (type) {
            case OUTPUT:
                return new DigitalOutput(type, settings, arduinoManager);
            case INPUT:
                return new DigitalInput(type, settings, arduinoManager);
            case ANALOG:
                return new AnalogInput(type, settings, arduinoManager);
            case MESSAGE:
                return new SendMessage(type, settings, arduinoManager);
            default:
                return new DigitalOutput(type, settings, arduinoManager);
        }
    }

    //COMMON METHODS
    public void activate() {
    }

    public void deactivate() {
    }

    public void fire() {
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
