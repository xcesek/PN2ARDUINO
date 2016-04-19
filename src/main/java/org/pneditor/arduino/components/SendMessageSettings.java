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
public class SendMessageSettings extends ArduinoComponentSettings {

    private String message;

    public SendMessageSettings(ArduinoManager arduinoManager) {
        super(arduinoManager);
        super.setType(ArduinoComponentType.MESSAGE);
        super.setPanel(getMyPanel());
        super.setInfoPanel(initInfoPanel());
    }

    private JPanel getMyPanel() {
        GridLayout customSettingsLayout = new GridLayout(0, 2);
        JPanel myPanel = new JPanel(customSettingsLayout);

        myPanel.add(new JLabel("Message: ", SwingConstants.LEFT));

        JTextField messageTextField = new JTextField(message);
        messageTextField.setPreferredSize(new Dimension(95, 25));

        myPanel.add(messageTextField);

        myPanel.add(Box.createVerticalStrut(5)); //2
        myPanel.add(Box.createVerticalStrut(5)); //3

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        message = ((JTextField) panel.getComponent(1)).getText();
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
        inputContent.append("- Message: custom string, not longer then 14 characters" + newline);

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
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        actualizeSettingsGUI();
    }


}
