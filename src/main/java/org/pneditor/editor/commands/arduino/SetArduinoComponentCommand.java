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
package org.pneditor.editor.commands.arduino;

import org.pneditor.arduino.components.common.ArduinoComponent;
import org.pneditor.petrinet.Node;
import org.pneditor.util.Command;

/**
 * Set label to clicked element
 *
 * @author Martin Riesz <riesz.martin at gmail.com>
 */
public class SetArduinoComponentCommand implements Command {

    private Node node;
    private ArduinoComponent newArduinoComponent;
    private ArduinoComponent oldArduinoComponent;

    public SetArduinoComponentCommand(Node node, ArduinoComponent newArduinoComponent) {
        this.node = node;
        this.newArduinoComponent = newArduinoComponent;
    }

    public void execute() {
        this.oldArduinoComponent = node.getArduinoComponent();
        node.setArduinoComponent(newArduinoComponent);
    }

    public void undo() {
        node.setArduinoComponent(oldArduinoComponent);
    }

    public void redo() {
        execute();
    }

    @Override
    public String toString() {
        return "Set arduino component to " + newArduinoComponent;
    }

}
