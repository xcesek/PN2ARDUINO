package org.pneditor.petrinet.xml;

import org.pneditor.arduino.components.*;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.petrinet.Node;

import javax.xml.bind.annotation.XmlElement;


public class XmlArduinoComponentSettings {
    //ADD ARDUINO COMPONENT SETTINGS

    // ANALOG
    @XmlElement(name = "bottomThreshold")
    public Integer bottomThreshold;

    @XmlElement(name = "upThreshold")
    public Integer upThreshold;

    // MESSAGE + CUSTOM SYSEX
    @XmlElement(name = "message")
    public String message;

    // CUSTOM SYSEX
    @XmlElement(name = "command")
    public String command;

    // PWM + SERVO
    @XmlElement(name = "value")
    public Integer value;

    public XmlArduinoComponentSettings() {
    }

    public XmlArduinoComponentSettings(ArduinoComponentSettings settings, Node node) {

        switch (settings.getType()) {
            case ANALOG:
                bottomThreshold = ((AnalogInputSettings) settings).getBottomThreshold();
                upThreshold = ((AnalogInputSettings) settings).getUpThreshold();
                break;
            case MESSAGE:
                message = ((SendMessageSettings) settings).getMessage();
                break;
            case CUSTOM_SYSEX:
                message = ((SendSysexCommandSettings) settings).getMessage();
                command = ((SendSysexCommandSettings) settings).getCommand().name();
                break;
            case PWM:
                value = ((PWMSettings) settings).getValue();
                break;
            case SERVO:
                value = ((ServoSettings) settings).getValue();
                break;
            default:
                bottomThreshold = null;
                upThreshold = null;
                message = null;
                command = null;
                value = null;
        }
    }
}
