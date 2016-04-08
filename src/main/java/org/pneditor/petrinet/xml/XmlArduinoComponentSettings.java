package org.pneditor.petrinet.xml;

import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.DigitalOutputSettings;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.Place;

import javax.xml.bind.annotation.XmlElement;


public class XmlArduinoComponentSettings {
    @XmlElement(name = "period")
    public double period;


    public XmlArduinoComponentSettings() {
    }

    public XmlArduinoComponentSettings(ArduinoComponentSettings settings, Node node) {
        period = 0.0;

        switch (settings.getType()) {
            case OUTPUT:
                if(node instanceof Place) {
                    period = ((DigitalOutputSettings) settings).getPeriod();
                }
                break;
            default:
                period = 0.0;
        }
    }
}
