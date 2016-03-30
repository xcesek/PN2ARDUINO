package org.pneditor.arduino;

import org.pneditor.petrinet.Node;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public interface ArduinoListener {
    public void updateFiredTransition(Node transition);
    public void updateActivatingPlace(Node place);
    public void updateDeactivatingPlace(Node place);
    public void update(Node sourcePlace, Node transition, Node destinationPlace);
}
