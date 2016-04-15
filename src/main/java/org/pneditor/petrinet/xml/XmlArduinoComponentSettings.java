package org.pneditor.petrinet.xml;

import org.pneditor.arduino.components.AnalogInputSettings;
import org.pneditor.arduino.components.DigitalOutputSettings;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.petrinet.Node;

import javax.xml.bind.annotation.XmlElement;


public class XmlArduinoComponentSettings {
    //ADD ARDUINO COMPONENT SETTINGS

    // ANALOG
    @XmlElement(name = "bottomThreshold")
    public Double bottomThreshold;

    @XmlElement(name = "upThreshold")
    public Double upThreshold;



    public XmlArduinoComponentSettings() {
    }

    public XmlArduinoComponentSettings(ArduinoComponentSettings settings, Node node) {

        switch (settings.getType()) {
            case ANALOG:
                bottomThreshold = ((AnalogInputSettings) settings).getBottomThreshold();
                upThreshold = ((AnalogInputSettings) settings).getUpThreshold();
                break;
            default:
                bottomThreshold = null;
                upThreshold = null;
        }
    }
}
