package org.pneditor.petrinet.xml;

import org.pneditor.arduino.manager.ArduinoNodeExtension;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Pavol Cesek on 3/10/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class XmlArduinoNodeExtension {
    @XmlElement(name = "pin")
    public String pin;

    @XmlElement(name = "function")
    public String function;

    public XmlArduinoNodeExtension() {
    }

    public XmlArduinoNodeExtension(ArduinoNodeExtension arduinoNodeExtension) {
        if (arduinoNodeExtension.isEnabled()) {
            pin = arduinoNodeExtension.getPin().name();
            function = arduinoNodeExtension.getFunction().name();
        }
    }

}
