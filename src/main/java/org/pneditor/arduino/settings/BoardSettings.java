package org.pneditor.arduino.settings;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class BoardSettings {
    private static final String DEFAULT_PORT_WIN = "COM8";
    private static final String DEFAULT_PORT_LIN = "/dev/ttyACM0";
    private String port;
    private BoardType boardType;
    private boolean verboseOutput;
    private boolean preserveTempFiles;

    public BoardSettings() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("win")) {
            port = DEFAULT_PORT_WIN;
        } else {
            port = DEFAULT_PORT_LIN;
        }

        boardType = BoardType.ARDUINO_UNO;
        verboseOutput = false;
        preserveTempFiles = false;
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

    public boolean isVerboseOutput() {
        return verboseOutput;
    }

    public void setVerboseOutput(boolean verboseOutput) {
        this.verboseOutput = verboseOutput;
    }

    public boolean isPreserveTempFiles() {
        return preserveTempFiles;
    }

    public void setPreserveTempFiles(boolean preserveTempFiles) {
        this.preserveTempFiles = preserveTempFiles;
    }
}
