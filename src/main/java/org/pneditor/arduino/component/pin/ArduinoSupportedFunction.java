package org.pneditor.arduino.component.pin;

/**
 * Created by Pavol Cesek on 3/10/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public enum ArduinoSupportedFunction {
    DIGITAL_IN {
        @Override
        public String toString() {
            return "Digital IN";
        }
    },
    DIGITAL_OUT {
        @Override
        public String toString() {
            return "Digital OUT";
        }
    },
    ANALOG_IN {
        @Override
        public String toString() {
            return "Analog IN";
        }
    },
    ANALOG_OUT {
        @Override
        public String toString() {
            return "Analog OUT (PWM)";
        }
    }
}
