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

import org.pneditor.arduino.ArduinoComponentType;
import org.pneditor.editor.Root;
import org.pneditor.petrinet.Node;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddArduinoComponentAction extends AbstractAction {

    private Root root;

    public AddArduinoComponentAction(Root root) {
        this.root = root;
        String name = "Add Arduino Component";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/arduino/digitalOutput.png"));
        putValue(SHORT_DESCRIPTION, name);
//		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
//		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("R"));
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() != null
                && root.getClickedElement() instanceof Node) {
            Node clickedNode = (Node) root.getClickedElement();

            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(getPanel());


            JDialog dialog = optionPane.createDialog(null, "Width 100");
            dialog.setVisible(true);


    }

}

    public Object[] getPanel() {
        //POP UP
        Object[] objects = new Object[8];

        //combobox - select arduino component
        final JComboBox arduinoComponentComboBox = new JComboBox(ArduinoComponentType.values());
        arduinoComponentComboBox.setSelectedIndex(0);
        objects[0] = arduinoComponentComboBox;

        //separator
        objects[1] = new JSeparator(JSeparator.HORIZONTAL);

        //custom bomboBox for properties
        final JComboBox pinComboBox = new JComboBox();
        JComboBox intervalCombobox = null;

        //pin
        final DefaultComboBoxModel digitalOutputPins = new DefaultComboBoxModel(new Integer[]{1, 2, 3});
        final DefaultComboBoxModel digitalInputPins = new DefaultComboBoxModel(new Integer[]{4, 5, 6});

        pinComboBox.setModel(digitalOutputPins);

        arduinoComponentComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ArduinoComponentType.DigitalOutput == arduinoComponentComboBox.getSelectedItem()) {
                    pinComboBox.setModel(digitalOutputPins);
                } else {
                    pinComboBox.setModel(digitalInputPins);
                }
            }
        });

        //separator
        objects[2] = Box.createVerticalStrut(5); // a vertical spacer

        objects[3] = pinComboBox;

        //properties
        JTextField pinField = new JTextField(2);
        objects[4] = new JLabel("Pin:");
        objects[5] = pinField;
        //separator
        //myPanel.add(Box.createHorizontalStrut(15)); // a spacer

        JTextField intervalField = new JTextField(5);
        objects[6] = (new JLabel("Interval:"));
        objects[7] = (intervalField);

        return objects;
    }

//    public JPanel getPanel() {
//        //POP UP
//        JPanel myPanel = new JPanel(new BorderLayout());
//
//        //combobox - select arduino component
//        JComboBox arduinoComponentComboBox = new JComboBox(ArduinoComponentType.values());
//        arduinoComponentComboBox.setSelectedIndex(0);
//        myPanel.add(arduinoComponentComboBox);
//
//        //separator
//        myPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);
//
//        //custom bomboBox for properties
//        JComboBox pinComboBox = new JComboBox();
//        JComboBox intervalCombobox = null;
//
//        //pin
//        DefaultComboBoxModel digitalOutputPins = new DefaultComboBoxModel(new Integer[]{1, 2, 3});
//        DefaultComboBoxModel digitalInputPins = new DefaultComboBoxModel(new Integer[]{4, 5, 6});
//
//        pinComboBox.setModel(digitalOutputPins);
//
//        arduinoComponentComboBox.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if (ArduinoComponentType.DigitalOutput == arduinoComponentComboBox.getSelectedItem()) {
//                    pinComboBox.setModel(digitalOutputPins);
//                } else {
//                    pinComboBox.setModel(digitalInputPins);
//                }
//            }
//        });
//
//        //separator
//        myPanel.add(Box.createVerticalStrut(5)); // a vertical spacer
//
//        myPanel.add(pinComboBox);
//
//        //properties
//        JTextField pinField = new JTextField(2);
//        myPanel.add(new JLabel("Pin:"));
//        myPanel.add(pinField);
//        //separator
//        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
//
//        JTextField intervalField = new JTextField(5);
//        myPanel.add(new JLabel("Interval:"));
//        myPanel.add(intervalField);
//
//        return myPanel;
//    }
}
