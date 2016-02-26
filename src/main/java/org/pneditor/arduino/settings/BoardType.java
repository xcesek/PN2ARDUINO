package org.pneditor.arduino.settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public enum BoardType {
    ARDUINO_UNO("Arduino Uno"),
    ARDUINO_NANO("Arduino Nano");

    private final String boardName;

    BoardType(String value) {
        boardName = value;
    }

    public String getBoardName() {
        return boardName;
    }

    public static Object[] getBoardNames() {
        List<String> names = new ArrayList(values().length);

        for (BoardType boardType : values()) {
            names.add(boardType.getBoardName());
        }

        return names.toArray();
    }

    public static BoardType byName(String name) {

        for (BoardType boardType : values()) {
            if (boardType.getBoardName().equals(name)) {
                return boardType;
            }
        }

        return null;
    }



}
