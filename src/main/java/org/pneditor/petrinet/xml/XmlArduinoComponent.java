package org.pneditor.petrinet.xml;

import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.petrinet.Node;

import javax.xml.bind.annotation.XmlElement;


public class XmlArduinoComponent {
    @XmlElement(name = "pin")
    public int pin;

    @XmlElement(name = "type")
    public String type;

    @XmlElement(name = "settings")
    public XmlArduinoComponentSettings settings;



    public XmlArduinoComponent() {
    }

    public XmlArduinoComponent(ArduinoComponent arduinoComponent, Node node) {

        pin = arduinoComponent.getSettings().getPin();
        type = arduinoComponent.getType().name();
        settings = new XmlArduinoComponentSettings(arduinoComponent.getSettings(), node);

    }

}
