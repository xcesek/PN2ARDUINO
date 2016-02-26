package org.pneditor.arduino.codeGeneration.upload.board;

import org.pneditor.arduino.codeGeneration.upload.response.UploadResponse;
import org.pneditor.arduino.codeGeneration.upload.response.VerifyResponse;

import java.util.List;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoNanoUploader implements ICodeUploader {
    private final static String PACKAGE = "arduino";
    private final static String ARCH = "avr";
    private final static String BOARD = "nano";

    private String portName;
    private List<String> sketchFiles;

    public UploadResponse upload() {
        return null;
    }

    public VerifyResponse verify() {
        return null;
    }

    public void setPort(String portName) {
        this.portName = portName;
    }

    public String getDescription() {
        return "Arduino Nano";
    }
}
