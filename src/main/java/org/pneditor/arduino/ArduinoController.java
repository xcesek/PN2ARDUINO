package org.pneditor.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.PlaceNode;
import org.pneditor.petrinet.Transition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void update(List<Node> sourcePlaces, Node transition, List<Node> destinationPlaces) {
        new Thread(() -> {
            try {
                if(sourcePlaces.size() != 0) {
                    for(Node place : sourcePlaces) {
                        if(place.hasArduinoComponent() && ((Marking)marking).getTokens((PlaceNode) place) == 0) {
                            place.getArduinoComponent().deactivate();
                        }
                    }
                }

                if(transition.hasArduinoComponent()) {
                    transition.getArduinoComponent().fire();
                }

                if(destinationPlaces.size() != 0) {
                    for(Node place : destinationPlaces) {
                        if(place.hasArduinoComponent() && ((Marking)marking).getTokens((PlaceNode) place) > 0) {
                            place.getArduinoComponent().activate();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }



}
