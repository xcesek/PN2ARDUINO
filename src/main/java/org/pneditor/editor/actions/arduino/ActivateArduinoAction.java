/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pneditor.editor.actions.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.firmata.FirmataDevice;
import org.pneditor.arduino.ArduinoController;
import org.pneditor.arduino.BoardSettings;
import org.pneditor.arduino.DeviceEventListener;
import org.pneditor.editor.PNEditor;
import org.pneditor.editor.Root;
import org.pneditor.editor.RootPflow;
import org.pneditor.editor.canvas.Canvas;
import org.pneditor.util.GraphicsTools;
import org.pneditor.util.LogEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class ActivateArduinoAction extends AbstractAction {

    private Root root;
    private IODevice device;
    private BoardSettings boardSettings;

    private boolean alreadyActivated;

    public ActivateArduinoAction(Root root) {
        this.root = root;
        String name = "Activate arduino";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/activateArduino16.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);
        alreadyActivated = false;

        boardSettings = root.getArduinoManager().getBoardSettings();
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {

            //doska
            root.getArduinoManager().setDevice(new FirmataDevice(boardSettings.getPort()));
            try {
                root.getArduinoManager().getDevice().start();
                root.getArduinoManager().getDevice().ensureInitializationIsDone();
                root.getArduinoManager().initializePinMap();
                root.getArduinoManager().getDevice().addEventListener(new DeviceEventListener());
                PNEditor.getRoot().getLogEditor().log("Arduino device is now ready", LogEditor.logType.ARDUINO);
                //ARDUINO CONTROLLER INITIALIZATION
                ArduinoController arduinoController = new ArduinoController(root.getArduinoManager(), root.getDocument().getPetriNet().getInitialMarking());
                alreadyActivated = true;
            } catch (IOException exception) {
                if (exception.getCause().toString().contains("Port busy")) {
                    PNEditor.getRoot().getLogEditor().log("Port busy / Device is already started", LogEditor.logType.ARDUINO);
                } else if (exception.getCause().toString().contains("Port not found")) {
                    PNEditor.getRoot().getLogEditor().log("Port not found (check if arduino board is connected)", LogEditor.logType.ARDUINO);
                } else {
                    PNEditor.getRoot().getLogEditor().log("ERROR: Cannot start Firmata device!", LogEditor.logType.ARDUINO);
                    exception.printStackTrace();
                }
            } catch (InterruptedException exception) {
                PNEditor.getRoot().getLogEditor().log("Connection timeout", LogEditor.logType.ARDUINO);
                exception.printStackTrace();
            }
        }
    }

    public boolean isAlreadyActivated() {
        return alreadyActivated;
    }
}


