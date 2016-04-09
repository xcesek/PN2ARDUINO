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
package org.pneditor.petrinet.xml;

import org.pneditor.arduino.manager.ArduinoManager;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class XmlArduinoManager {
    @XmlElement(name = "board")
    public String board;

    @XmlElement(name = "port")
    public String port;

    @XmlElement(name = "verbosetOutput")
    public boolean verbosetOutput;

    @XmlElement(name = "preserveTempFiles")
    public boolean preserveTempFiles;

    @XmlElement(name = "timingPolicyType")
    public String timingPolicyType;

    @XmlElement(name = "firingPolicyType")
    public String firingPolicyType;

    // default constructor needed by automatic xml mapping
    public XmlArduinoManager() {
    }

    public XmlArduinoManager(ArduinoManager arduinoManager) {
        port = arduinoManager.getBoardSettings().getPort();
        board = arduinoManager.getBoardSettings().getBoardType().getBoardName();
        verbosetOutput = arduinoManager.getBoardSettings().isVerboseOutput();
        preserveTempFiles = arduinoManager.getBoardSettings().isPreserveTempFiles();
        timingPolicyType = arduinoManager.getTimingPolicyType().name();
        firingPolicyType = arduinoManager.getFiringPolicyType().name();
    }

}
