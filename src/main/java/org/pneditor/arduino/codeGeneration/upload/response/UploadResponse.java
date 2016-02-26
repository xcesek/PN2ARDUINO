package org.pneditor.arduino.codeGeneration.upload.response;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class UploadResponse {
    private int responseCode;
    private String cmdOutput;
    private String customMessage;

    public UploadResponse() {
    }


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getCmdOutput() {
        return cmdOutput;
    }

    public void setCmdOutput(String cmdOutput) {
        this.cmdOutput = cmdOutput;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}
