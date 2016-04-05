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
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.editor.Root;
import org.pneditor.editor.RootPflow;
import org.pneditor.util.GraphicsTools;
import org.pneditor.util.LogEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GenerateCodeAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private boolean alreadyGenerated;

    public GenerateCodeAction(Root root) {
        this.root = root;
        String name = "Generate code";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/code.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);
        alreadyGenerated = false;

        arduinoManager = root.getArduinoManager();
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {

            writeCodeToConsole("Generating code for Arduino from current Petri Net.");
            long startTime = System.currentTimeMillis();
            CodeGenerator codeGenerator = new CodeGenerator(arduinoManager, root.getCurrentMarking());
            String generatedCode = codeGenerator.generate();
            long endTime = System.currentTimeMillis();

            writeCodeToConsole(generatedCode);

            if((endTime - startTime) < 1000 ) {
                writeCodeToConsole("Generating took " + (endTime - startTime) + " milliseconds.");
            }else{
                writeCodeToConsole("Generating took " + ((endTime - startTime) / 1000) + " seconds.");
            }
            alreadyGenerated = true;
            root.refreshAll();
        }
    }

    public boolean isAlreadyGenerated() {
        return alreadyGenerated;
    }

    private void writeCodeToConsole(String logMessage) {
        LogEditor logEditor = ((RootPflow) root).getLogEditor();
        logEditor.log(logMessage, LogEditor.logType.ARDUINO);
    }
}
