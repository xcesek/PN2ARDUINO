package org.pneditor.arduino.manager;

import org.pneditor.arduino.component.device.DelayOccurrenceType;
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

    private DelayOccurrenceType delayOccurrenceType = DelayOccurrenceType.NO;
    private boolean inverseLogic = false;
    private int thresholdRangeLow = -1;
    private int thresholdRangeHigh = -1;

    private boolean enabled = false;


    public ArduinoNodeExtension() {
    }

    public ArduinoPin getPin() {
        return pin;
    }

    public void setPin(ArduinoPin pin) {
        this.pin = pin;
    }

    public void setFunction(ArduinoSupportedFunction function) {
        this.function = function;
    }

    public ArduinoSupportedFunction getFunction() {
        return function;
    }

    public DelayOccurrenceType getDelayOccurrenceType() {
        return delayOccurrenceType;
    }

    public void setDelayOccurrenceType(DelayOccurrenceType delayOccurrenceType) {
        this.delayOccurrenceType = delayOccurrenceType;
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
