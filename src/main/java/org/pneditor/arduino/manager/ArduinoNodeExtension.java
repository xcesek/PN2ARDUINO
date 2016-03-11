package org.pneditor.arduino.manager;

import org.pneditor.arduino.component.ArduinoPin;
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
    private boolean enabled;


    public ArduinoNodeExtension() {
        enabled = false;
    }

    public ArduinoNodeExtension(ArduinoPin pin, ArduinoSupportedFunction function) {
        this.pin = pin;
        this.function = function;
        enabled = true;
    }

    public ArduinoNodeExtension(String pinStr, String functionStr) {
        try {
            this.pin = ArduinoPin.valueOf(pinStr);
            this.function = ArduinoSupportedFunction.valueOf(functionStr);
            enabled = true;
        } catch (Exception e) {
            enabled = false;
        }
    }

    public ArduinoPin getPin() {
        return pin;
    }


    public ArduinoSupportedFunction getFunction() {
        return function;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
