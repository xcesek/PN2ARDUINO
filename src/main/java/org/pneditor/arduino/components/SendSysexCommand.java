package org.pneditor.arduino.components;

import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.firmata.parser.FirmataToken;
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
public class SendSysexCommand extends ArduinoComponent {

    private String myMessage;

    public SendSysexCommand(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        super(type, settings, arduinoManager, node);
        color = Color.lightGray;
        arduinoManager.getDevice().addEventListener(new EventListener());
    }

    @Override
    public void fire() {
        int messageLength = 3;
        byte[] msg = new byte[messageLength];
        msg[0] = FirmataToken.START_SYSEX;
        msg[1] = ((SendSysexCommandSettings) settings).getCommand().getCommandIdentifier();
        msg[2] = FirmataToken.END_SYSEX;

        try {
            arduinoManager.getDevice().sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class EventListener implements IODeviceEventListener {

        @Override
        public void onStart(IOEvent ioEvent) {

        }

        @Override
        public void onStop(IOEvent ioEvent) {

        }

        @Override
        public void onPinChange(IOEvent ioEvent) {

        }

        @Override
        public void onI2cMessageReceive(IOEvent ioEvent, byte b, byte b1, byte[] bytes) {

        }

        @Override
        public void onMessageReceive(IOEvent ioEvent, String s) {
            System.out.println("Prisla sprava: " + s);
        }
    }

}
