package org.pneditor.arduino;

import org.firmata4j.IODevice;

import java.io.File;

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

    public IODevice getDevice() {
        return device;
    }

    public void setDevice(IODevice device) {
        this.device = device;
    }

    public void updateSettings(String port, String board) {
        boardSettings.setPort(port);
    }

    public ArduinoManager() {
        boardSettings = new BoardSettings();    // todo
    }

    public BoardSettings getBoardSettings() {
        return boardSettings;
    }

    public String getProjectDirName() {
        return SOURCE_DIR_NAME + File.separator + PROJECT_DIR_NAME;
    }
}
