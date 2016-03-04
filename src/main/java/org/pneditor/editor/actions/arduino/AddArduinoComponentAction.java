package org.pneditor.editor.actions.arduino;

import org.pneditor.arduino.ArduinoComponent;
import org.pneditor.arduino.ArduinoComponentType;
import org.pneditor.editor.Root;
import org.pneditor.editor.commands.SetLabelCommand;
import org.pneditor.editor.commands.arduino.SetArduinoComponentCommand;
import org.pneditor.petrinet.Node;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class AddArduinoComponentAction extends AbstractAction {

    private Root root;
    //POP UP
    ArrayList<Object> objectList = new ArrayList<Object>();

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
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog(null, "Arduino Component");
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            //if OK
            int value = ((Integer)optionPane.getValue()).intValue();
            if (value == JOptionPane.YES_OPTION) {
                ArduinoComponent arduinoComponent = new ArduinoComponent(13, ArduinoComponentType.Output);
                root.getUndoManager().executeCommand(new SetArduinoComponentCommand(clickedNode, arduinoComponent));
            }

    }

}

    public Object[] getPanel() {

        //select arduino component
        JPanel arduinoComponentComboBoxPanel = new JPanel();

        final JComboBox arduinoComponentComboBox = new JComboBox(ArduinoComponentType.values());
        arduinoComponentComboBox.setSelectedIndex(0);

        arduinoComponentComboBoxPanel.add(new JLabel("Choose Arduino Component: "));
        arduinoComponentComboBoxPanel.add(arduinoComponentComboBox);

        objectList.add(arduinoComponentComboBoxPanel);

        //separator
        objectList.add(new JSeparator(JSeparator.HORIZONTAL));
        //vertical spacer
        objectList.add(Box.createVerticalStrut(5));

        //custom properties
        //**pin
        final JComboBox pinComboBox = new JComboBox();

        //TODO ake piny

        final DefaultComboBoxModel outputPins = new DefaultComboBoxModel(new Integer[]{13, 2, 3});
        final DefaultComboBoxModel inputPins = new DefaultComboBoxModel(new Integer[]{4, 5, 6});

        pinComboBox.setModel(outputPins); //defaultny model
        pinComboBox.setSelectedIndex(0);

        arduinoComponentComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ArduinoComponentType.Output == arduinoComponentComboBox.getSelectedItem()) {
                    pinComboBox.setModel(outputPins);
                } else {
                    pinComboBox.setModel(inputPins);
                }
            }
        });

        objectList.add(pinComboBox);
//
//        //properties
//        JTextField pinField = new JTextField(2);
//        objects[4] = new JLabel("Pin:");
//        objects[5] = pinField;
//        //separator
//        //myPanel.add(Box.createHorizontalStrut(15)); // a spacer
//
//        JTextField intervalField = new JTextField(5);
//        objects[6] = (new JLabel("Interval:"));
//        objects[7] = (intervalField);


        return objectList.toArray();
    }


}
