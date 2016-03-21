package org.pneditor.arduino;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class BoardSettings {
    private static final String DEFAULT_PORT = "COM5";
    private String port;

    public BoardSettings() {
            port = DEFAULT_PORT;
    }

    public BoardSettings(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
