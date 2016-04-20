package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;
import org.pneditor.petrinet.Node;
import org.pneditor.util.LogEditor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class ServoSettings extends ArduinoComponentSettings {

    private Integer value = 0;
    private Node node;

    public ServoSettings(ArduinoManager arduinoManager, Integer pin, Node node) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.SERVO);
        if (pin != null) {
            arduinoManager.getUsedPins().add(pin.byteValue());
        }
        super.pin = pin;
        super.setPanel(getMyPanel());
        this.node = node;
        super.setInfoPanel(initInfoPanel());
    }

    private JPanel getMyPanel() {
        JPanel myPanel = new JPanel(new GridLayout(0, 2));

        myPanel.add(new JLabel("Pin: ")); //0

        //PIN
        Object[] comboBoxModel = arduinoManager.getUnusedPins(type);
        JComboBox pinComboBox;
        // * if node already has arduino component - load from clickedNode
        if (getPin() != null) {
            Object[] newComboBoxModel = new Object[comboBoxModel.length + 1];
            newComboBoxModel[0] = pin.byteValue();
            int i = 1;
            for (Object o : comboBoxModel) {
                newComboBoxModel[i++] = o;
            }
            pinComboBox = new JComboBox(newComboBoxModel);
            pinComboBox.setSelectedItem(pin.byteValue());
        } else {
            if(comboBoxModel.length == 0) {
                PNEditor.getRoot().getLogEditor().log("There is no more pin! / Error in communication with Arduino, please restart PNEditor.", LogEditor.logType.ARDUINO);
                return myPanel;
            }
            pinComboBox = new JComboBox(comboBoxModel);
            pinComboBox.setSelectedIndex(0);
        }
        pinComboBox.setPreferredSize(new Dimension(50, 25));

        myPanel.add(pinComboBox); //1

        myPanel.add(Box.createVerticalStrut(5)); //2
        myPanel.add(Box.createVerticalStrut(5)); //3

        JLabel valueLabel = new JLabel("Value: ", SwingConstants.LEFT);
        JTextField valueTextField = new JTextField(((Integer) value).toString());

        myPanel.add(valueLabel); //4
        myPanel.add(valueTextField);  //5

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        pin = (((Byte) (((JComboBox) panel.getComponent(1)).getSelectedItem())).intValue());
        try {
            value = Integer.parseInt(((JTextField) panel.getComponent(5)).getText());
            if (value < 0) {
                value = 0;
            }
            if (value > 180) {
                value = 180;
            }
        } catch (NumberFormatException e) {
            PNEditor.getRoot().getLogEditor().log("Unsupported format of Value!", LogEditor.logType.ARDUINO);
            value = 0;
        }


        //Mark pin as used
        arduinoManager.getUsedPins().add(pin.byteValue());
    }

    @Override
    public void actualizeSettingsGUI() {
        super.setPanel(getMyPanel());
    }

    private JPanel initInfoPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        String newline = "\n";

        JTextArea inputTitle = new JTextArea();
        inputTitle.setEditable(false);
        inputTitle.setFont(new Font("Serif", Font.BOLD, 13));
        inputTitle.append("Input value: ");

        JTextArea inputContent = new JTextArea();
        inputContent.setEditable(false);
        inputContent.setFont(new Font("Serif", Font.PLAIN, 12));
        inputContent.append("- Value: integer from <0-180>" + newline);

        JTextArea netTitle = new JTextArea();
        netTitle.setEditable(false);
        netTitle.setFont(new Font("Serif", Font.BOLD, 13));
        netTitle.append("Petri net support: ");

        JTextArea netContent = new JTextArea();
        netContent.setEditable(false);
        netContent.setFont(new Font("Serif", Font.PLAIN, 12));
        netContent.append("- firing transition with delay" + newline);
        netContent.append("- place" + newline);

        panel.add(inputTitle);
        panel.add(inputContent);

        panel.add(netTitle);
        panel.add(netContent);


        return panel;
    }

    //GETTER & SETTER
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        actualizeSettingsGUI();
    }
}
