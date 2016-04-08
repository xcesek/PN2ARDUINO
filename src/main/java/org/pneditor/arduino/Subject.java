package org.pneditor.arduino;

import org.pneditor.petrinet.Node;

import java.util.List;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public interface Subject {
    public void registerArduinoListener(ArduinoListener arduinoListener);
    public void removeArduinoListener(ArduinoListener arduinoListener);
    public void notifyArduinoListeners(List<Node> sourcePlaces, Node transition, List<Node> destinationPlacse);
}
