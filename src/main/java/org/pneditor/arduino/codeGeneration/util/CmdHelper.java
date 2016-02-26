package org.pneditor.arduino.codeGeneration.util;

import org.apache.commons.exec.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.pneditor.arduino.codeGeneration.upload.response.UploadResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class CmdHelper {
    private static final int DEFAULT_WATCHDOG_TIME = 30000;

    public static UploadResponse runCommand(String board, String port,  List<File> sourceFiles) {
        UploadResponse uploadResponse = new UploadResponse();

        String line = "arduino --upload --board " + board + " --port " + port + " " + getSourceFilesString(sourceFiles);
        CommandLine cmdLine = CommandLine.parse(line);

        DefaultExecutor executor = new DefaultExecutor();
        //DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        //ExecuteWatchdog watchdog = new ExecuteWatchdog(DEFAULT_WATCHDOG_TIME);

        try {
            //executor.setWatchdog(watchdog);
            executor.setStreamHandler(streamHandler);

            int exitValue = executor.execute(cmdLine);

            //resultHandler.waitFor();
            String output = outputStream.toString();

            uploadResponse.setCustomMessage("Upload successfull!");
            uploadResponse.setResponseCode(exitValue);
            uploadResponse.setCmdOutput(output);

        } catch (IOException e ) {
            uploadResponse.setCustomMessage("Upload failed!");
            uploadResponse.setResponseCode(-1);
            uploadResponse.setCmdOutput(e.getLocalizedMessage());
        }

        return uploadResponse;
    }

    private static String getSourceFilesString(List<File> sourceFiles){
        final StringBuffer buffer = new StringBuffer();
        sourceFiles.forEach(f -> buffer.append(f.getAbsolutePath()).append(" "));
        return buffer.toString();
    }
}
