package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.petrinet.Node;

import java.awt.*;
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
public class SendMessage extends ArduinoComponent {

    private String myMessage;

    public SendMessage(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        super(type, settings, arduinoManager, node);
        color = Color.GRAY;
    }

    @Override
    public void fire() {
        try {
            myMessage = ((SendMessageSettings) settings).getMessage();
            arduinoManager.getDevice().sendMessage(myMessage);
            System.out.println("Message was sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
