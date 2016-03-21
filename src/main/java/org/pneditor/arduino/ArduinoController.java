package org.pneditor.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.PlaceNode;

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

        //PORT SETTINGS
        try {
            this.device.getPin(13).setMode(Pin.Mode.OUTPUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Pin 13 set to OUTPUT mode.");
    }

    @Override
    public void update(Node node) {

        new Thread(() -> {
            try {
          //      if(((Marking)marking).getTokens((PlaceNode)node) > 0) {
                    device.getPin(13).setValue(device.getPin(13).getValue() == 1 ? 0 : 1);
        //        }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();


    }
}
