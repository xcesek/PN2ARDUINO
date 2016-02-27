package org.pneditor.arduino.generator.upload.board;

import org.apache.commons.io.FileUtils;
import org.pneditor.arduino.generator.upload.response.UploadResponse;
import org.pneditor.arduino.generator.util.CmdHelper;

import java.io.File;
import java.util.List;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoUnoUploader implements CodeUploader {
    private final static String PACKAGE = "arduino";
    private final static String ARCH = "avr";
    private final static String BOARD = "uno";

    private final static String[] extensions = { "ino" };

    private String portName;
    private String projectDirName;

    public ArduinoUnoUploader() {
    }

    public UploadResponse upload() {
        File projectDir = new File(projectDirName);
        List<File> sketchFiles = (List<File>) FileUtils.listFiles(projectDir, extensions, false);

        return CmdHelper.runCommand(getBoard(), portName, sketchFiles);
    }

    public String getBoard() {
        return PACKAGE + ":" + ARCH + ":" + BOARD;
    }

    public void setPort(String portName) {
        this.portName = portName;
    }

    public String getProjectDirName() {
        return projectDirName;
    }

    public void setProjectDirName(String projectDirName) {
        this.projectDirName = projectDirName;
    }
}
