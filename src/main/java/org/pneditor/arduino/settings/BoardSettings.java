package org.pneditor.arduino.settings;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class BoardSettings {
    private String port;
    private BoardType boardType;

    public BoardSettings() {
    }

    public BoardSettings(String port, BoardType boardType) {
        this.port = port;
        this.boardType = boardType;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
