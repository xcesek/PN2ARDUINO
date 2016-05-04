package org.pneditor.arduino.generator.upload;

import org.pneditor.arduino.generator.upload.board.ArduinoMegaUploader;
import org.pneditor.arduino.generator.upload.board.ArduinoNanoUploader;
import org.pneditor.arduino.generator.upload.board.ArduinoUnoUploader;
import org.pneditor.arduino.settings.BoardType;
import org.pneditor.arduino.generator.upload.board.CodeUploader;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class CodeUploaderFactory {

    public static CodeUploader getCodeUploader(BoardType boardType) {
        CodeUploader selectedBoard;

        switch (boardType) {
            case ARDUINO_UNO:
                selectedBoard = new ArduinoUnoUploader();
            break;
            case ARDUINO_NANO:
                selectedBoard = new ArduinoNanoUploader();
                break;
            case ARDUINO_MEGA:
                selectedBoard = new ArduinoMegaUploader();
                break;
            default:
                selectedBoard = new ArduinoUnoUploader();
        }
        return selectedBoard;
    }
}
