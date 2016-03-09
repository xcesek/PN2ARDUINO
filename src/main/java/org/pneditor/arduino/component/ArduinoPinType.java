package org.pneditor.arduino.component;


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
