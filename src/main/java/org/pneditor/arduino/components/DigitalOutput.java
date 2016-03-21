package org.pneditor.arduino.components;

import org.pneditor.arduino.components.settings.ArduinoComponentSettings;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class DigitalOutput extends ArduinoComponent{

    public DigitalOutput(int pin, ArduinoComponentType type, ArduinoComponentSettings settings) {
        super(pin, type, settings);
    }
}
