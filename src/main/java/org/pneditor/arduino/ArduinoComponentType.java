package org.pneditor.arduino;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public enum ArduinoComponentType {
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
