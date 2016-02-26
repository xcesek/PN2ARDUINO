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

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.pneditor.arduino.codeGeneration.upload.board.ArduinoUnoUploader;
import org.pneditor.arduino.codeGeneration.upload.response.UploadResponse;
import org.pneditor.editor.Root;
import org.pneditor.petrinet.Node;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class UploadCodeAction extends AbstractAction {

    private Root root;

    public UploadCodeAction(Root root) {
        this.root = root;
        String name = "Upload code";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/setupBoard.gif"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {

            String[] extensions = { "ino" };
            List<File> sourceFiles = (List<File>) FileUtils.listFiles(new File("Palo"), extensions, false);

            ArduinoUnoUploader uploader = new ArduinoUnoUploader();
            uploader.setPort("COM8");
            uploader.setSketchFiles(sourceFiles);

            UploadResponse response = uploader.upload();

            JOptionPane.showMessageDialog(root.getParentFrame(),
                    response.getCmdOutput());


        }
    }
}
