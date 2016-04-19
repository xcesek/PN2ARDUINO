package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;
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
public class AnalogInputSettings extends ArduinoComponentSettings {

    private double bottomThreshold = 0;
    private double upThreshold = 1023;

    public AnalogInputSettings(ArduinoManager arduinoManager, Integer pin) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.ANALOG);
        if (pin != null) {
            arduinoManager.getUsedPins().add(pin.byteValue());
        }
        super.pin = pin;
        super.setPanel(getMyPanel());
        super.setInfoPanel(initInfoPanel());
    }

    private JPanel getMyPanel() {
        JPanel myPanel = new JPanel(new GridLayout(0, 2));

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
            pinComboBox = new JComboBox(comboBoxModel);
            pinComboBox.setSelectedIndex(0);
        }
        myPanel.add(new JLabel("Pin: ")); //0
        myPanel.add(pinComboBox); //1

        myPanel.add(Box.createVerticalStrut(5)); //2
        myPanel.add(Box.createVerticalStrut(5)); //3

        JCheckBox treshholdEnable = new JCheckBox("Enable Treshhold Logic", false);

        myPanel.add(treshholdEnable); //4
        myPanel.add(Box.createHorizontalGlue()); //5

        JLabel botomLabel = new JLabel("Bottom Threshold: ", SwingConstants.LEFT);
        JTextField bottomTextField = new JTextField(((Double) bottomThreshold).toString());
        JLabel upLabel = new JLabel("Up Threshold: ", SwingConstants.LEFT);
        JTextField upTextField = new JTextField(((Double) upThreshold).toString());

        botomLabel.setEnabled(false);
        bottomTextField.setEnabled(false);
        upLabel.setEnabled(false);
        upTextField.setEnabled(false);

        myPanel.add(botomLabel); //6
        myPanel.add(bottomTextField);  //7
        myPanel.add(upLabel); //8
        myPanel.add(upTextField); //9

        myPanel.add(Box.createVerticalStrut(5));
        myPanel.add(Box.createVerticalStrut(5));

        treshholdEnable.addChangeListener(e -> {
            if(treshholdEnable.isSelected()) {
                botomLabel.setEnabled(true);
                bottomTextField.setEnabled(true);
                upLabel.setEnabled(true);
                upTextField.setEnabled(true);
            } else {
                botomLabel.setEnabled(false);
                bottomTextField.setEnabled(false);
                upLabel.setEnabled(false);
                upTextField.setEnabled(false);
            }
        });

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        pin = (((Byte) (((JComboBox) panel.getComponent(1)).getSelectedItem())).intValue());
        if(((JCheckBox) panel.getComponent(4)).isSelected()) {
            try{
                bottomThreshold = Double.parseDouble (((JTextField) panel.getComponent(7)).getText());
                upThreshold = Double.parseDouble (((JTextField) panel.getComponent(9)).getText());
                if(bottomThreshold < 0 ) {
                    bottomThreshold = 0;
                }
                if(upThreshold > 1023 ) {
                    upThreshold = 1023;
                }
                if(bottomThreshold > upThreshold) {
                    bottomThreshold = 0;
                    upThreshold = 1023;
                }
            } catch(NumberFormatException e) {
                PNEditor.getRoot().getLogEditor().log("Unsupported format of Threshold!", LogEditor.logType.ARDUINO);
                bottomThreshold = 0;
                upThreshold = 1023;
            }

        } else {
            bottomThreshold = 0;
            upThreshold = 1023;
        }

        //Mark pin as used
        arduinoManager.getUsedPins().add(pin.byteValue());
    }

    @Override
    public void actualizeSettingsGUI(){
        super.setPanel(getMyPanel());
    }

    private JPanel initInfoPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        String newline = "\n";

        JTextArea featuresTitle = new JTextArea();
        featuresTitle.setEditable(false);
        featuresTitle.setFont(new Font("Serif", Font.BOLD, 13));
        featuresTitle.append("Supported Features: ");

        JTextArea featuresContent = new JTextArea(3, 40);
        featuresContent.setEditable(false);
        featuresContent.setFont(new Font("Serif", Font.PLAIN, 12));
        featuresContent.append("- if you Enable Threshold logic, you can enter custom Bottom and Up Threshold" + newline);
        featuresContent.append("- default value of Bottom Threshol is 0" + newline);
        featuresContent.append("- default value of Up Threshol is 1023" + newline);

        JTextArea inputTitle = new JTextArea();
        inputTitle.setEditable(false);
        inputTitle.setFont(new Font("Serif", Font.BOLD, 13));
        inputTitle.append("Input value: ");

        JTextArea inputContent = new JTextArea();
        inputContent.setEditable(false);
        inputContent.setFont(new Font("Serif", Font.PLAIN, 12));
        inputContent.append("- Bottom Threshold: greater than 0 & lower than Up Threshold" + newline);
        inputContent.append("- Up Threshold: lower than 1023 & greater than Bottom Threshold" + newline);

        JTextArea netTitle = new JTextArea();
        netTitle.setEditable(false);
        netTitle.setFont(new Font("Serif", Font.BOLD, 13));
        netTitle.append("Petri net support: ");

        JTextArea netContent = new JTextArea();
        netContent.setEditable(false);
        netContent.setFont(new Font("Serif", Font.PLAIN, 12));
        netContent.append("- condition if transition is fireable" + newline);

        panel.add(featuresTitle);
        panel.add(featuresContent);

        panel.add(inputTitle);
        panel.add(inputContent);

        panel.add(netTitle);
        panel.add(netContent);


        return panel;
    }

    //GETTER & SETTER

    public double getBottomThreshold() {
        return bottomThreshold;
    }

    public void setBottomThreshold(double bottomThreshold) {
        this.bottomThreshold = bottomThreshold;
        actualizeSettingsGUI();
    }

    public double getUpThreshold() {
        return upThreshold;
    }

    public void setUpThreshold(double upThreshold) {
        this.upThreshold = upThreshold;
        actualizeSettingsGUI();
    }
}
