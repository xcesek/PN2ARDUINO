package org.pneditor.arduino;

import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.pneditor.editor.PNEditor;
import org.pneditor.util.LogEditor;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class DeviceEventListener implements IODeviceEventListener {
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
