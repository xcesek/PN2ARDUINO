package org.pneditor.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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