/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pneditor.petrinet;

import org.pneditor.arduino.manager.ArduinoNodeExtension;

/**
 * Represents place in Petri net
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class Place extends PlaceNode implements Cloneable {
    private ArduinoNodeExtension arduinoNodeExtension = new ArduinoNodeExtension();
    private int capacity = 10;
    private boolean isStatic = false;

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public ArduinoNodeExtension getArduinoNodeExtension() {
        return arduinoNodeExtension;
    }

    public void setArduinoNodeExtension(ArduinoNodeExtension arduinoNodeExtension) {
        this.arduinoNodeExtension = arduinoNodeExtension;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
