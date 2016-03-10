package org.pneditor.arduino.component;


/**
 * Created by Pavol Cesek on 3/10/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public enum ArduinoPin {
    D2(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT
    }, ArduinoPinType.DIGITAL, 2),
    D3(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.SERVO
    }, ArduinoPinType.DIGITAL, 3),
    D4(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT
    }, ArduinoPinType.DIGITAL, 4),
    D5(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.SERVO
    }, ArduinoPinType.DIGITAL, 5),
    D6(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.SERVO
    }, ArduinoPinType.DIGITAL, 6),
    D7(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT
    }, ArduinoPinType.DIGITAL, 8),
    D8(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT
    }, ArduinoPinType.DIGITAL, 8),
    D9(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.SERVO
    }, ArduinoPinType.DIGITAL, 9),
    D10(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.SERVO
    }, ArduinoPinType.DIGITAL, 10),
    D11(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT,
            ArduinoSupportedFunction.ANALOG_OUT,
            ArduinoSupportedFunction.SERVO
    }, ArduinoPinType.DIGITAL, 11),
    D12(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT
    }, ArduinoPinType.DIGITAL, 12),
    D13(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.DIGITAL_IN,
            ArduinoSupportedFunction.DIGITAL_OUT
    }, ArduinoPinType.DIGITAL, 13),

    A0(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.ANALOG_IN,
    }, ArduinoPinType.ANALOG, 0),
    A1(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.ANALOG_IN,
    }, ArduinoPinType.ANALOG, 1),
    A2(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.ANALOG_IN,
    }, ArduinoPinType.ANALOG, 2),
    A3(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.ANALOG_IN,
    }, ArduinoPinType.ANALOG, 3),
    A4(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.ANALOG_IN,
    }, ArduinoPinType.ANALOG, 4),
    A5(new ArduinoSupportedFunction[]{
            ArduinoSupportedFunction.ANALOG_IN,
    }, ArduinoPinType.ANALOG, 5);


    private ArduinoSupportedFunction[] supportedFunctions;
    private ArduinoPinType pinDirection;
    private int number;

    ArduinoPin(ArduinoSupportedFunction[] supportedFunctions, ArduinoPinType pinDirection, int number) {
        this.supportedFunctions = supportedFunctions;
        this.pinDirection = pinDirection;
        this.number = number;
    }

    public ArduinoSupportedFunction[] getSupportedFunctions() {
        return supportedFunctions;
    }

    public ArduinoPinType getPinDirection() {
        return pinDirection;
    }

    public int getNumber() {
        return number;
    }
}
