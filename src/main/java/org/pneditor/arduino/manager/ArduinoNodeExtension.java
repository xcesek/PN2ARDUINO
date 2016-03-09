package org.pneditor.arduino.manager;

import org.pneditor.arduino.component.ArduinoPinDirection;
import org.pneditor.arduino.component.ArduinoPinType;
import org.pneditor.petrinet.Node;

/**
 * Created by Pavol Cesek on 3/9/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoNodeExtension {
    private ArduinoPinType pinType;
    private ArduinoPinDirection pinDirection;
    private int pinNumber;

    public ArduinoNodeExtension() {
    }

    public ArduinoNodeExtension(ArduinoPinType pinType, ArduinoPinDirection pinDirection, int pinNumber) {
        this.pinType = pinType;
        this.pinDirection = pinDirection;
        this.pinNumber = pinNumber;
    }

    public ArduinoPinType getPinType() {
        return pinType;
    }

    public void setPinType(ArduinoPinType pinType) {
        this.pinType = pinType;
    }

    public ArduinoPinDirection getPinDirection() {
        return pinDirection;
    }

    public void setPinDirection(ArduinoPinDirection pinDirection) {
        this.pinDirection = pinDirection;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }
}
