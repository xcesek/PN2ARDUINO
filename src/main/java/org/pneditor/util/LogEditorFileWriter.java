package org.pneditor.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Laci on 07.03.2016.
 */
public class LogEditorFileWriter {
    private final static String DIR_NAME = "log";
    private File logFile;

    public LogEditorFileWriter(String logFileName) {
        File logDir = new File(DIR_NAME);
        if (!logDir.exists()){
            logDir.mkdirs();
        }
        logFile = new File(logDir, logFileName);
    }

    public void write(String message) {
        try {
            FileUtils.write(logFile, message, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}