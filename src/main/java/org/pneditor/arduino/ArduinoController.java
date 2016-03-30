package org.pneditor.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.PlaceNode;
import org.pneditor.petrinet.Transition;

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
public class ArduinoController implements ArduinoListener {
    private Subject marking;
    private IODevice device;
    private ArduinoManager arduinoManager;

    public ArduinoController(ArduinoManager arduinoManager, Subject marking) {
        this.marking = marking;
        this.device = arduinoManager.getDevice();
        marking.registerArduinoListener(this);

        //PORT SETTINGS - ak otvaram ulozenu

    }


    @Override
    public void updateFiredTransition(Node transition) {
        new Thread(() -> {
            try {
                if(transition.hasArduinoComponent()) {
                    transition.getArduinoComponent().fire();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    @Override
    public void updateActivatingPlace(Node place) {
        new Thread(() -> {
            try {
                if(place.hasArduinoComponent()) {
                    place.getArduinoComponent().activate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    public void updateDeactivatingPlace(Node place) {
        new Thread(() -> { //TODO ak deaktivujem a jemu este ostali tokeny
            try {
                if(place.hasArduinoComponent()) {
                    place.getArduinoComponent().deactivate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    @Override
    public void update(Node sourcePlace, Node transition, Node destinationPlace) {
        new Thread(() -> {
            try {
                if(sourcePlace.hasArduinoComponent()) {
                    sourcePlace.getArduinoComponent().deactivate();
                }
                if(transition.hasArduinoComponent()) {
                    transition.getArduinoComponent().fire();
                }
                if(destinationPlace.hasArduinoComponent()) {
                    destinationPlace.getArduinoComponent().activate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
}
