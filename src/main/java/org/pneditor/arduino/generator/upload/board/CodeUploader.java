package org.pneditor.arduino.generator.upload.board;

import org.pneditor.arduino.generator.upload.response.UploadResponse;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.arduino.settings.BoardSettings;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public interface CodeUploader {

    UploadResponse upload();

    void setBoardSettings(BoardSettings boardSettings);

    void setProjectDirName(String projectDirName);
}
