package org.pneditor.arduino.components;

import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.firmata.parser.FirmataToken;
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
public class SendSysexCommand extends ArduinoComponent {

    public SendSysexCommand(ArduinoComponentType type, ArduinoComponentSettings settings, ArduinoManager arduinoManager, Node node) {
        super(type, settings, arduinoManager, node);
        color = Color.lightGray;
        arduinoManager.getDevice().addEventListener(EventListener.getInstance());
    }

    @Override
    public void fire() {
        String message = ((SendSysexCommandSettings) settings).getMessage();
        if(message.length() > 14) {
            PNEditor.getRoot().getLogEditor().log("Your message is longer then 14 characters, it would by trimmed.", LogEditor.logType.ARDUINO);
            ((SendSysexCommandSettings) settings).setMessage(message.substring(0,13));
        }
        byte[] bytes = ((SendSysexCommandSettings) settings).getMessage().getBytes();
        byte[] msg = new byte[bytes.length * 2 + 3];

        msg[0] = FirmataToken.START_SYSEX;
        msg[1] = ((SendSysexCommandSettings) settings).getCommand().getCommandIdentifier();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            msg[i * 2 + 2] = (byte) (b & 0x7F);
            msg[i * 2 + 3] = (byte) ((b >> 7) & 0x7F);
        }
        msg[msg.length - 1] = FirmataToken.END_SYSEX;

        try {
            arduinoManager.getDevice().sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate(){
        fire();
    }

   @Override
    public boolean isEnabled(){
        return true;
    }



    private static class EventListener implements IODeviceEventListener {
        private static EventListener eventListener;

        private EventListener(){}

        public static EventListener getInstance(){
            if(eventListener == null) {
                eventListener = new EventListener();
            }
            return eventListener;
        }

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
            PNEditor.getRoot().getLogEditor().log("Arduino sent message: " + s, LogEditor.logType.ARDUINO);
        }
    }

}
