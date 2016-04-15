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

    private boolean withDelay = false;
    private boolean inverseLogic = false;
    private int thresholdRangeLow = -1;
    private int thresholdRangeHigh = -1;

    private boolean enabled = false;


    public ArduinoNodeExtension() {
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

    public void setThresholdRangeLow(int thresholdRangeLow) {
        this.thresholdRangeLow = thresholdRangeLow;
    }

    public void setThresholdRangeHigh(int thresholdRangeHigh) {
        this.thresholdRangeHigh = thresholdRangeHigh;
    }

    public boolean getInverseLogic() {
        return inverseLogic;
    }

    public int getThresholdRangeLow() {
        return thresholdRangeLow;
    }

    public int getThresholdRangeHigh() {
        return thresholdRangeHigh;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
