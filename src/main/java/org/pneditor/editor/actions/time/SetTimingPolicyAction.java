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
package org.pneditor.editor.actions.time;

import org.pneditor.editor.Root;
import org.pneditor.arduino.time.TimingPolicyType;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class SetTimingPolicyAction extends AbstractAction {

    private Root root;

    public SetTimingPolicyAction(Root root) {
        this.root = root;
        String name = "Set Timing Policy";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/clock.png"));
        putValue(SHORT_DESCRIPTION, name);
//		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
//		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("R"));
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {

        Object[] timingPolicyNames = TimingPolicyType.values();
        TimingPolicyType answer = (TimingPolicyType) JOptionPane.showInputDialog(
                root.getParentFrame(),
                "Select Timing Policy:",
                "Select Timing Policy:",
                JOptionPane.PLAIN_MESSAGE,
                null,
                timingPolicyNames,
                root.getArduinoManager().getTimingPolicyType());
        if ((answer != null)) {
            root.getArduinoManager().setTimingPolicyType(answer);
        }

        return;
    }


}
