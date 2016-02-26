package org.pneditor.arduino.codeGeneration.upload;

import org.pneditor.arduino.codeGeneration.upload.board.ArduinoUnoUploader;
import org.pneditor.arduino.settings.BoardType;
import org.pneditor.arduino.codeGeneration.upload.board.ICodeUploader;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class CodeUploaderFactory {

    public ICodeUploader getCodeUploader(BoardType boardType) {
        ICodeUploader selectedBoard;

        switch (boardType) {
            case ARDUINO_UNO:
                selectedBoard = new ArduinoUnoUploader();
            break;
            case ARDUINO_NANO:
                selectedBoard = new ArduinoUnoUploader();
                break;
            default:
                selectedBoard = new ArduinoUnoUploader();
        }
        return selectedBoard;
    }
}
