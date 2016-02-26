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

import org.apache.commons.io.FileUtils;
import org.pneditor.editor.Root;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GenerateCodeAction extends AbstractAction {

    private Root root;

    public GenerateCodeAction(Root root) {
        this.root = root;
        String name = "Generate code";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/compile.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(true);
    }

    // FAKE: NOT GENERATING YET, INSTEAD JUST COPY SOME EXISTING SKETCH TO DESIRED DESTINATION
    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            new File("generated").mkdir();

            File source = new File("c:\\Users\\palo\\Documents\\Arduino\\Palo\\Palo.ino");
            File dest = new File("c:\\Users\\palo\\school\\diplomovka\\PN2ARDUINO\\Palo\\Palo.ino");


            try {
                FileUtils.copyFile(source, dest);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }
}
