/*
 * Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pneditor.editor.actions.arduino;

import org.pneditor.arduino.generator.upload.CodeUploaderFactory;
import org.pneditor.arduino.generator.upload.board.CodeUploader;
import org.pneditor.arduino.generator.upload.response.UploadResponse;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.arduino.settings.BoardSettings;
import org.pneditor.editor.Root;
import org.pneditor.editor.RootPflow;
import org.pneditor.editor.canvas.Canvas;
import org.pneditor.util.GraphicsTools;
import org.pneditor.util.LogEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class UploadCodeAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    public UploadCodeAction(Root root) {
        this.root = root;
        String name = "Upload code";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/upload.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);

        arduinoManager = root.getArduinoManager();
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            BoardSettings boardSettings = arduinoManager.getBoardSettings();

            CodeUploader codeUploader = CodeUploaderFactory.getCodeUploader(boardSettings.getBoardType());
            codeUploader.setBoardSettings(boardSettings);
            codeUploader.setProjectDirName(arduinoManager.getProjectDirName());

            final Canvas canvas = ((RootPflow) root).getCanvas();
            final Cursor oldCursor = canvas.getCursor();
            canvas.activeCursor = GraphicsTools.getCursor("icons/clock.png", new Point(16, 16));
            canvas.setCursor(canvas.activeCursor);
            root.repaintCanvas();

            writeCodeToConsole("Starting to upload code. Pleas wait. It may take even one minute!");
            long startTime = System.currentTimeMillis();
            new Thread(() -> {
                UploadResponse response = codeUploader.upload();
                long endTime = System.currentTimeMillis();

                writeCodeToConsole(response.getCmdOutput());
                writeCodeToConsole("Uploading finished.");
                if ((endTime - startTime) < 1000) {
                    writeCodeToConsole("Uploading took " + (endTime - startTime) + " milliseconds.");
                } else {
                    writeCodeToConsole("Uploading took " + ((endTime - startTime) / 1000) + " seconds.");
                }

                canvas.activeCursor = oldCursor;
                canvas.setCursor(canvas.activeCursor);
                root.repaintCanvas();
            }).start();

        }
    }

    private void writeCodeToConsole(String logMessage) {
        LogEditor logEditor = ((RootPflow) root).getLogEditor();
        logEditor.log(logMessage, LogEditor.logType.PNEDITOR);
    }
}
