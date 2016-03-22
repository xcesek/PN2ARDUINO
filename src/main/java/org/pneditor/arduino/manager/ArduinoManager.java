package org.pneditor.arduino.manager;

import org.pneditor.arduino.settings.BoardSettings;
import org.pneditor.arduino.settings.BoardType;
import org.pneditor.arduino.time.TimingPolicyType;

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
    private TimingPolicyType timingPolicyType;


    public void updateSettings(String port, String board, boolean verboseOutput, boolean preserveTemp) {
        boardSettings.setPort(port);
        boardSettings.setBoardType(BoardType.byName(board));
        boardSettings.setVerboseOutput(verboseOutput);
        boardSettings.setPreserveTempFiles(preserveTemp);
    }

    public ArduinoManager() {
        boardSettings = new BoardSettings();    // todo
        timingPolicyType = TimingPolicyType.Deterministic;
    }

    public BoardSettings getBoardSettings() {
        return boardSettings;
    }

    public String getProjectDirName() {
        return SOURCE_DIR_NAME + File.separator + PROJECT_DIR_NAME;
    }

    public TimingPolicyType getTimingPolicyType() {
        return timingPolicyType;
    }

    public void setTimingPolicyType(TimingPolicyType timingPolicyType) {
        this.timingPolicyType = timingPolicyType;
    }
}
