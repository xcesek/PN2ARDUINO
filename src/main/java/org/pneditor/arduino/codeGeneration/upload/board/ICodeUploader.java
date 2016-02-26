package org.pneditor.arduino.codeGeneration.upload.board;

import org.pneditor.arduino.codeGeneration.upload.response.UploadResponse;
import org.pneditor.arduino.codeGeneration.upload.response.VerifyResponse;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public interface ICodeUploader {

    UploadResponse upload();

}
