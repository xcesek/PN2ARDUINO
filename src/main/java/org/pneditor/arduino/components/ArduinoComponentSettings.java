package org.pneditor.arduino.components;

import org.pneditor.arduino.components.place.PlaceDigitalOutputSettings;
import org.pneditor.arduino.components.transition.TransitionDigitalOutputSettings;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.Place;

import javax.swing.*;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
abstract public class ArduinoComponentSettings {

    private JPanel panel;
    private ArduinoComponentType type;

    public JPanel getSettingsGui() {
        return panel;
    }

    public ArduinoComponentType getType(){
        return type;
    }

    public static ArduinoComponentSettings settingsFactory(ArduinoComponentType type, Node node) {
        switch (type) {
            case OUTPUT:
                if(node instanceof Place) {
                    return new PlaceDigitalOutputSettings();
                } else {
                    return new TransitionDigitalOutputSettings();
                }
            default:
                return new PlaceDigitalOutputSettings();
        }
    }

    public void parseSettingsGUI(JPanel panel) {
    }

   //GETTER & SETTER


    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setType(ArduinoComponentType type) {
        this.type = type;
    }
}
