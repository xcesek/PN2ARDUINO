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

import org.pneditor.arduino.generator.generate.CodeGenerator;
import org.pneditor.arduino.generator.upload.CodeUploaderFactory;
import org.pneditor.arduino.generator.upload.board.CodeUploader;
import org.pneditor.arduino.generator.upload.response.UploadResponse;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.arduino.settings.BoardSettings;
import org.pneditor.arduino.settings.BoardType;
import org.pneditor.editor.Root;
import org.pneditor.editor.RootPflow;
import org.pneditor.util.GraphicsTools;
import org.pneditor.util.LogEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.Callable;


public class UploadWithPopupAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private JComboBox boardTypeCombo;
    final JTextField portField;

    public UploadWithPopupAction(Root root) {
        this.root = root;
        String name = "Generate and upload code";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/bug.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);

        arduinoManager = root.getArduinoManager();
        boardTypeCombo = new JComboBox(BoardType.getBoardNames());
        portField = new JTextField(2);
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            BoardSettings boardSettings = arduinoManager.getBoardSettings();

            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(popupContent(boardSettings));
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Setup, compile and upload to Arduino");
            dialog.setVisible(true);

            // set values
            boardSettings.setPort(portField.getText());

            String boardTypeStr = (String) boardTypeCombo.getSelectedItem();
            if (boardTypeStr != null && !boardTypeStr.isEmpty()) {
                boardSettings.setBoardType(BoardType.byName((String) boardTypeCombo.getSelectedItem()));
            } else {
                boardSettings.setBoardType(BoardType.valueOf(BoardType.ARDUINO_UNO.getBoardName()));
            }

            int value = (optionPane.getValue() != null) ? ((Integer) optionPane.getValue()).intValue() : JOptionPane.CANCEL_OPTION;
            if (value == JOptionPane.YES_OPTION) {

                // generate
                CodeGenerator codeGenerator = new CodeGenerator(arduinoManager, root.getCurrentMarking());
                writeCodeToConsole("Generating code for Arduino from current Petri Net.");
                String generatedCode = codeGenerator.generate();
                writeCodeToConsole(generatedCode);

                // upload
                CodeUploader codeUploader = CodeUploaderFactory.getCodeUploader(boardSettings.getBoardType());
                codeUploader.setBoardSettings(boardSettings);
                codeUploader.setProjectDirName(arduinoManager.getProjectDirName());

                writeCodeToConsole("Starting to upload code.");
                long startTime = System.currentTimeMillis();
                new Thread(() -> {
                    UploadResponse response = codeUploader.upload();
                    long endTime = System.currentTimeMillis();

                    writeCodeToConsole(response.getCmdOutput());
                    writeCodeToConsole("Uploading finished.");
                    writeCodeToConsole("Uploading took " + (endTime - startTime) + " milliseconds.");

                }).start();

            }
        }
    }

    private Object[] popupContent(final BoardSettings boardSettings) {
        Object[] objects = new Object[4];

        objects[0] = (new JLabel("Arduino Board Type:"));

        boardTypeCombo.setSelectedItem(boardSettings.getBoardType() == null ? BoardType.ARDUINO_UNO.getBoardName() : boardSettings.getBoardType().getBoardName());
        objects[1] = boardTypeCombo;

        objects[2] = (new JLabel("Port:"));

        portField.setText(boardSettings.getPort());
        objects[3] = portField;

        return objects;
    }

    private void writeCodeToConsole(String logMessage) {
        LogEditor logEditor = ((RootPflow) root).getLogEditor();
        logEditor.log(logMessage, LogEditor.logType.ARDUINO);
    }
}
