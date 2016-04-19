package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.arduino.components.common.CustomSysexCommand;
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
public class SendSysexCommandSettings extends ArduinoComponentSettings {

    private CustomSysexCommand command;
    private String message;

    public SendSysexCommandSettings(ArduinoManager arduinoManager) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.CUSTOM_SYSEX);
        super.setPanel(getMyPanel());
        super.setInfoPanel(initInfoPanel());
    }

    private JPanel getMyPanel() {
        GridLayout customSettingsLayout = new GridLayout(0, 2);
        JPanel myPanel = new JPanel(customSettingsLayout);

        JComboBox commandComboBox = new JComboBox(CustomSysexCommand.values());
        if (command != null) {
            commandComboBox.setSelectedItem(command);
        } else {
            commandComboBox.setSelectedIndex(0);
        }

        myPanel.add(new JLabel("Command: ", SwingConstants.LEFT)); //0
        myPanel.add(commandComboBox); //1

        myPanel.add(Box.createVerticalStrut(5)); //2
        myPanel.add(Box.createVerticalStrut(5)); //3

        myPanel.add(new JLabel("Message: ", SwingConstants.LEFT)); //4

        JTextField messageTextField = new JTextField(message);
        messageTextField.setPreferredSize(new Dimension(95, 25));

        myPanel.add(messageTextField); //5

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        command = (CustomSysexCommand) ((JComboBox) panel.getComponent(1)).getSelectedItem();
        message = ((JTextField) panel.getComponent(5)).getText();
        if(message.length() > 14) {
            PNEditor.getRoot().getLogEditor().log("Your message is longer then 14 characters, it would by trimmed.", LogEditor.logType.ARDUINO);
            message = message.substring(0,13);
        }
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
        inputContent.append("- Command: choise of custom command byte value" + newline);
        inputContent.append("- Message: custom message, not longer than 14 characters" + newline);

        JTextArea netTitle = new JTextArea();
        netTitle.setEditable(false);
        netTitle.setFont(new Font("Serif", Font.BOLD, 13));
        netTitle.append("Petri net support: ");

        JTextArea netContent = new JTextArea();
        netContent.setEditable(false);
        netContent.setFont(new Font("Serif", Font.PLAIN, 12));
        netContent.append("- firing transition" + newline);
        netContent.append("- place" + newline);

        panel.add(inputTitle);
        panel.add(inputContent);

        panel.add(netTitle);
        panel.add(netContent);


        return panel;
    }

    //GETTER & SETTER
    public CustomSysexCommand getCommand() {
        return command;
    }

    public void setCommand(CustomSysexCommand command) {
        this.command = command;
        actualizeSettingsGUI();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        actualizeSettingsGUI();
    }


}
