package org.pneditor.petrinet.xml;

import org.pneditor.arduino.components.ArduinoComponentSettings;
import org.pneditor.arduino.components.place.PlaceDigitalOutput;
import org.pneditor.arduino.components.place.PlaceDigitalOutputSettings;
import org.pneditor.arduino.components.transition.TransitionDigitalOutputSettings;
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
                    period = ((PlaceDigitalOutputSettings) settings).getPeriod();
                }
            default:
                period = 0.0;
        }
    }
}
