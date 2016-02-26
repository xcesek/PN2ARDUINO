package org.pneditor.arduino.codeGeneration.upload.board;

import org.pneditor.arduino.codeGeneration.upload.response.UploadResponse;
import org.pneditor.arduino.codeGeneration.upload.response.VerifyResponse;
import org.pneditor.arduino.codeGeneration.util.CmdHelper;

import java.io.File;
import java.util.List;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoUnoUploader implements ICodeUploader {
    private final static String PACKAGE = "arduino";
    private final static String ARCH = "avr";
    private final static String BOARD = "uno";

    private String portName;
    private List<File> sketchFiles;

    public UploadResponse upload() {

        return CmdHelper.runCommand(getBoard(), portName, sketchFiles);
    }

    public String getBoard() {
        return PACKAGE + ":" + ARCH + ":" + BOARD;
    }

    public void setPort(String portName) {
        this.portName = portName;
    }

    public void setSketchFiles(List<File> sketchFiles) {
        this.sketchFiles = sketchFiles;
    }

    public String getDescription() {
        return "Arduino uno";
    }
}
