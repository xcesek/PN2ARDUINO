package org.pneditor.petrinet.xml;

import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.petrinet.Node;

import javax.xml.bind.annotation.XmlElement;


public class XmlArduinoComponent {
    @XmlElement(name = "pin")
    public Integer pin;

    @XmlElement(name = "type")
    public String type;

    @XmlElement(name = "settings")
    public XmlArduinoComponentSettings settings;



    public XmlArduinoComponent() {
    }

    public XmlArduinoComponent(ArduinoComponent arduinoComponent, Node node) {

       if(node.hasArduinoComponent()) {
           if(arduinoComponent.getSettings().getPin() != null) {
               pin = arduinoComponent.getSettings().getPin();
           } else {
               pin = null;
           }
           type = arduinoComponent.getType().name();
           settings = new XmlArduinoComponentSettings(arduinoComponent.getSettings(), node);
       }
    }

}
