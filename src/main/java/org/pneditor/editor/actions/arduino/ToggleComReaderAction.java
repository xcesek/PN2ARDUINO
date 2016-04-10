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

import org.apache.commons.lang3.StringUtils;
import org.pneditor.arduino.bridge.ArduinoComReader;
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


public class ToggleComReaderAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;
    private ArduinoComReader comReader;

    public ToggleComReaderAction(Root root) {
        this.root = root;
        String name = "Toggle serial connection to Arduino board";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/terminal.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);

        arduinoManager = root.getArduinoManager();

    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            BoardSettings boardSettings = arduinoManager.getBoardSettings();

            if (boardSettings == null || StringUtils.isEmpty(boardSettings.getPort())) {
                JOptionPane.showMessageDialog(root.getParentFrame(), "Setup board first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (comReader == null) { // for the first time
                comReader = new ArduinoComReader(boardSettings.getPort(), ((RootPflow) root).getLogEditor());
                boolean initialized = comReader.init();
                JOptionPane.showMessageDialog(root.getParentFrame(), "Serial connection " + (initialized ? "" : "NOT") + " established", "Arduino serial connection", JOptionPane.INFORMATION_MESSAGE); // :)
            } else { // already initialized, just toggle
                if (comReader.isOpen()) {
                    boolean closed = comReader.close();
                    JOptionPane.showMessageDialog(root.getParentFrame(), "Serial connection " + (closed ? "closed" : "failed to close"), "Arduino serial connection", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    boolean initialized = comReader.init();
                    JOptionPane.showMessageDialog(root.getParentFrame(), "Serial connection " + (initialized ? "opened" : "failed to open"), "Arduino serial connection", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }


}
