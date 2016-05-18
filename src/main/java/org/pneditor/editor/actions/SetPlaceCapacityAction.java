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
package org.pneditor.editor.actions;

import org.pneditor.editor.PNEditor;
import org.pneditor.editor.Root;
import org.pneditor.editor.RootPflow;
import org.pneditor.petrinet.Place;
import org.pneditor.util.GraphicsTools;
import org.pneditor.util.LogEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class SetPlaceCapacityAction extends AbstractAction {

    private Root root;
    private JTextField capacityField;

    public SetPlaceCapacityAction(Root root) {
        this.root = root;
        String name = "Set place capacity";
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/activateArduino16.png"));
        setEnabled(false);

        capacityField = new JTextField(2);
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() instanceof Place) {
            Place place = (Place) root.getClickedElement();

            GridLayout gridLayout = new GridLayout(0, 2);
            gridLayout.setHgap(10);
            gridLayout.setVgap(10);
            JPanel popupPanel = new JPanel();
            popupPanel.setLayout(gridLayout);

            popupPanel.add(new JLabel("Set place capacity:"));
            capacityField.setText(String.valueOf(place.getCapacity()));
            popupPanel.add(capacityField);

            java.util.List<Object> objects = new ArrayList<>();
            objects.add(popupPanel);


            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(objects.toArray());

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Set place capacity");
            dialog.setVisible(true);

            int value = (optionPane.getValue() != null) ? ((Integer) optionPane.getValue()).intValue() : JOptionPane.CANCEL_OPTION;
            if (value == JOptionPane.YES_OPTION) {
                try {
                    Integer newCapacity = Integer.parseInt(capacityField.getText());
                    if(newCapacity < PNEditor.getRoot().getCurrentMarking().getTokens(place)) {
                        PNEditor.getRoot().getLogEditor().log("Capacity cannot be lower than actual marking.", LogEditor.logType.PNEDITOR);
                    } else {
                        place.setCapacity(Integer.parseInt(capacityField.getText()));
                    }
                } catch (NumberFormatException ignore) {
                    place.setCapacity(10);  // default
                    ((RootPflow) root).getLogEditor().log("Wrong value specified as place capacity. Setting to default value 10", LogEditor.logType.PNEDITOR);
                }

            }

        }
    }

}
