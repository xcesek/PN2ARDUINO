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
import org.pneditor.editor.Root;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class ActivateArduinoAction extends AbstractAction {

    private Root root;
    private IODevice device;
    private BoardSettings boardSettings;

    public ActivateArduinoAction(Root root) {
        this.root = root;
        String name = "Setup board";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/digitalOutput.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);

        boardSettings = root.getArduinoManager().getBoardSettings();
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            //doska
            root.getArduinoManager().setDevice(new FirmataDevice(boardSettings.getPort()));
            try {
                root.getArduinoManager().getDevice().start();
                root.getArduinoManager().getDevice().ensureInitializationIsDone();
                System.out.println("device is now ready!");
                //ARDUINO CONTROLLER INITIALIZATION
                ArduinoController arduinoController = new ArduinoController(root.getArduinoManager(), root.getDocument().getPetriNet().getInitialMarking());
            } catch (IOException exception) {
                System.out.println("!! ERROR: device start failed.");
                exception.printStackTrace();
            }catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}


