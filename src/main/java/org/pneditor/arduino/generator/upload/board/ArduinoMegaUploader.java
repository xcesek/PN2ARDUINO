package org.pneditor.arduino.generator.upload.board;

import org.apache.commons.io.FileUtils;
import org.pneditor.arduino.generator.upload.response.UploadResponse;
import org.pneditor.arduino.generator.util.CmdHelper;
import org.pneditor.arduino.settings.BoardSettings;

import java.io.File;
import java.util.List;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoMegaUploader implements CodeUploader {
    private final static String PACKAGE = "arduino";
    private final static String ARCH = "avr";
    private final static String BOARD = "mega";

    private final static String[] extensions = {"ino"};

    private String projectDirName;
    private BoardSettings boardSettings;

    public ArduinoMegaUploader() {
    }

    public UploadResponse upload() {
        File projectDir = new File(projectDirName);
        List<File> sketchFiles = (List<File>) FileUtils.listFiles(projectDir, extensions, false);

        StringBuilder additionalSwitches = new StringBuilder();
        if (boardSettings.isVerboseOutput()) {
            additionalSwitches.append(" --verbose ");
        }
        if(boardSettings.isPreserveTempFiles()) {
            additionalSwitches.append(" --preserve-temp-files ");
        }

        return CmdHelper.runCommand(getBoard(), boardSettings.getPort(), additionalSwitches.toString(), sketchFiles);
    }

    @Override
    public void setBoardSettings(BoardSettings boardSettings) {
        this.boardSettings = boardSettings;
    }

    @Override
    public void setProjectDirName(String projectDirName) {
        this.projectDirName = projectDirName;
    }

    public String getBoard() {
        return PACKAGE + ":" + ARCH + ":" + BOARD;
    }
}
