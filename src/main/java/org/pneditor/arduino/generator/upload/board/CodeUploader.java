package org.pneditor.arduino.generator.upload.board;

import org.pneditor.arduino.generator.upload.response.UploadResponse;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public interface CodeUploader {

    UploadResponse upload();

    String getBoard();

    void setPort(String portName);

    String getProjectDirName();

    void setProjectDirName(String projectDirName);
}
