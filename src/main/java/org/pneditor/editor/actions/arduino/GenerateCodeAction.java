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
import org.apache.commons.lang3.text.StrSubstitutor;
import org.pneditor.arduino.generator.generate.CodeGenerator;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.editor.Root;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateCodeAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private boolean alreadyGenerated;

    public GenerateCodeAction(Root root) {
        this.root = root;
        String name = "Generate code";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/compile.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);
        alreadyGenerated = false;

        arduinoManager = root.getArduinoManager();
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {

            CodeGenerator codeGenerator = new CodeGenerator(arduinoManager);
            String generatedCode = codeGenerator.generate();

            alreadyGenerated = true;
            root.refreshAll();

            JOptionPane.showMessageDialog(root.getParentFrame(),
                    generatedCode);
        }
    }

    public boolean isAlreadyGenerated() {
        return alreadyGenerated;
    }
}
