package org.pneditor.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.pneditor.arduino.components.common.ArduinoComponentType;

import java.io.File;
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
    public final static String ARDUINO_RES_DIR_NAME = "src/main/resources/arduino";
    public final static String SOURCE_DIR_NAME = "generated";
    public final static String PROJECT_DIR_NAME = "PetriNet";
    public final static String MAIN_SKETCH_FILE_NAME = "PetriNet.ino";
    public final static String SKETCH_TEMPLATE_NAME = "PetriNet.template";

    private BoardSettings boardSettings;
    private IODevice device;

    private Map<ArduinoComponentType, List<Byte>> pinMap;

    public ArduinoManager() {
        boardSettings = new BoardSettings();    // todo
    }

    public Map<ArduinoComponentType, List<Byte>> getPinMap() {
        return pinMap;
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

    public String getProjectDirName() {
        return SOURCE_DIR_NAME + File.separator + PROJECT_DIR_NAME;
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
