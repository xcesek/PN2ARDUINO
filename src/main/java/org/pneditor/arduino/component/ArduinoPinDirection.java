package org.pneditor.arduino.component;


public enum ArduinoPinDirection {
    OUTPUT{
        public String toString() {
            return "Output";
        }
    },
    INPUT{
        public String toString() {
            return "Input";
        }
    }
}
