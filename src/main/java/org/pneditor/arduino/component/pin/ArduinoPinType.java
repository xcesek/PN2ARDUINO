package org.pneditor.arduino.component.pin;


public enum ArduinoPinType {
    DIGITAL{
        public String toString() {
            return "Digital";
        }
    },
    ANALOG{
        public String toString() {
            return "Analog";
        }
    }
}
