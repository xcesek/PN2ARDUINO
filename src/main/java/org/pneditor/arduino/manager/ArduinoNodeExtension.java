package org.pneditor.arduino.manager;

import org.pneditor.arduino.component.pin.ArduinoPin;
import org.pneditor.arduino.component.pin.ArduinoSupportedFunction;

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

    private boolean withDelay;
    private boolean inverseLogic;
    private float thresholdRangeLow;
    private float thresholdRangeHigh;

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

    public boolean isWithDelay() {
        return withDelay;
    }

    public void setWithDelay(boolean withDelay) {
        this.withDelay = withDelay;
    }

    public void setInverseLogic(boolean inverseLogic) {
        this.inverseLogic = inverseLogic;
    }

    public void setThresholdRangeLow(float thresholdRangeLow) {
        this.thresholdRangeLow = thresholdRangeLow;
    }

    public void setThresholdRangeHigh(float thresholdRangeHigh) {
        this.thresholdRangeHigh = thresholdRangeHigh;
    }

    public boolean getInverseLogic() {
        return inverseLogic;
    }

    public float getThresholdRangeLow() {
        return thresholdRangeLow;
    }

    public float getThresholdRangeHigh() {
        return thresholdRangeHigh;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
