package org.pneditor.arduino.manager;

import org.pneditor.arduino.component.ArduinoPin;
import org.pneditor.arduino.component.ArduinoPinType;
import org.pneditor.arduino.component.ArduinoSupportedFunction;

/**
 * Created by Pavol Cesek on 3/9/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoNodeExtension {
    private ArduinoPin pin;
    private ArduinoSupportedFunction function;


    public ArduinoNodeExtension() {
    }

    public ArduinoNodeExtension(ArduinoPin pin, ArduinoSupportedFunction function) {
        this.pin = pin;
        this.function = function;
    }

    public ArduinoNodeExtension(String pinStr, String functionStr) {
        this.pin = ArduinoPin.valueOf(pinStr);
        this.function = ArduinoSupportedFunction.valueOf(functionStr);
    }

    public ArduinoPin getPin() {
        return pin;
    }

    public void setPin(ArduinoPin pin) {
        this.pin = pin;
    }

    public ArduinoSupportedFunction getFunction() {
        return function;
    }

    public void setFunction(ArduinoSupportedFunction function) {
        this.function = function;
    }
}
