package org.pneditor.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.pneditor.arduino.components.common.ArduinoComponentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavol Cesek on 2/27/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoManager {

    private BoardSettings boardSettings;
    private IODevice device;

    private Map<ArduinoComponentType, List<Byte>> pinMap;


    // METHODS
    public ArduinoManager() {
        boardSettings = new BoardSettings();    // todo
    }


    public IODevice getDevice() {
        return device;
    }
    public void setDevice(IODevice device) {
        this.device = device;
    }

    public void updateSettings(String port, String board) {
        boardSettings.setPort(port);
    }
    public BoardSettings getBoardSettings() {
        return boardSettings;
    }


    public Map<ArduinoComponentType, List<Byte>> getPinMap() {
        return pinMap;
    }

    public void initializePinMap() {
        Map<ArduinoComponentType, List<Byte>> modelMap = new HashMap<>();

        for (ArduinoComponentType type : ArduinoComponentType.values()) {
            modelMap.put(type, new ArrayList<>());
        }

        for (Pin pin : getDevice().getPins()) {
            for (Pin.Mode mode : pin.getSupportedModes()) {
                try {
                    ArduinoComponentType type = ArduinoComponentType.valueOf(mode.name());
                    modelMap.get(type).add(pin.getIndex());
                } catch (IllegalArgumentException e) {
                    System.out.println("Nepodporovany mod" + mode.name());
                } catch (Exception e) {
                    System.out.println("Nobody knows what happend");
                }
            }
        }


        pinMap = modelMap;
    }
}
