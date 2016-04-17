package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;
import org.pneditor.petrinet.Node;
import org.pneditor.util.LogEditor;

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
    public void activate() {
        try {
            myMessage = ((SendMessageSettings) settings).getMessage();
            arduinoManager.getDevice().sendMessage(myMessage);
            PNEditor.getRoot().getLogEditor().log("Message was sent", LogEditor.logType.ARDUINO);
        } catch (IOException e) {
            PNEditor.getRoot().getLogEditor().log("Message was not sent (Firmata 2.3.6 implementation has input buffer only 32 bytes so you can safely send only 15 characters log messages) ", LogEditor.logType.ARDUINO);
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate() {
    }

    @Override
    public void fire() {
        try {
            myMessage = ((SendMessageSettings) settings).getMessage();
            arduinoManager.getDevice().sendMessage(myMessage);
            PNEditor.getRoot().getLogEditor().log("Message was sent", LogEditor.logType.ARDUINO);
        } catch (IOException e) {
            PNEditor.getRoot().getLogEditor().log("Message was not sent (Firmata 2.3.6 implementation has input buffer only 32 bytes so you can safely send only 15 characters log messages) ", LogEditor.logType.ARDUINO);
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
