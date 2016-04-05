package org.pneditor.arduino.components;

import org.firmata4j.Pin;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public enum ArduinoComponentType{

    /**
     * Digital pin in input mode
     */
    INPUT{
        @Override
        public String toString() {
            return "Digital Input";
        }
    },
    /**
     * Digital pin in output mode
     */
    OUTPUT{
        @Override
        public String toString() {
            return "Digital Output";
        }
    },
    /**
     * Analog pin in analog input mode
     */
    ANALOG{
        @Override
        public String toString() {
            return "Analog Input";
        }
    },
    /**
     * Digital pin in PWM output mode
     */
    PWM{
        @Override
        public String toString() {
            return "Digital PWM Output";
        }
    },
    /**
     * Digital pin in Servo output mode
     */
    SERVO{
        @Override
        public String toString() {
            return "Digital Servo Output";
        }
    },
//    /**
//     * shiftIn/shiftOut mode
//     */
//    SHIFT{
//        @Override
//        public String toString() {
//            return "shiftIn/shiftOut";
//        }
//    },
    /**
     * Pin included in I2C setup
     */
    I2C{
        @Override
        public String toString() {
            return "I2C";
        }
    },
//    /**
//     * Pin configured for 1-wire
//     */
//    ONEWIRE{
//        @Override
//        public String toString() {
//            return "ONEWIRE";
//        }
//    },
//    /**
//     * Pin configured for stepper motor
//     */
//    STEPPER{
//        @Override
//        public String toString() {
//            return "Stepper motor";
//        }
//    },
//    /**
//     * Pin configured for rotary encoders
//     */
//    ENCODER{
//        @Override
//        public String toString() {
//            return "Encoder";
//        }
//    },
//    /**
//     * Pin configured for serial communication
//     */
//    SERIAL{
//        @Override
//        public String toString() {
//            return "Serial communication";
//        }
//    },
//    /**
//     * Enable internal pull-up resistor for pin
//     */
//    PULLUP{
//        @Override
//        public String toString() {
//            return "Pull-up resitor";
//        }
//    },
//
//    // add new modes here
//
//    /**
//     * Indicates a mode that this client library doesn't support
//     */
//    UNSUPPORTED{
//        @Override
//        public String toString() {
//            return "UNSUPPORTED";
//        }
//    },
//    /**
//     * Pin configured to be ignored by digitalWrite and capabilityResponse
//     */
//    IGNORED{
//        @Override
//        public String toString() {
//            return "IGNORED";
//        }
//    }
}
