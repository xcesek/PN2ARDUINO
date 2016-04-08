package org.pneditor.arduino.components;

import org.pneditor.arduino.ArduinoManager;
import org.pneditor.arduino.components.common.ArduinoComponentSettings;
import org.pneditor.arduino.components.common.ArduinoComponentType;

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
    }

    private JPanel getMyPanel() {
        GridLayout customSettingsLayout = new GridLayout(0, 2);
        JPanel myPanel = new JPanel(customSettingsLayout);

        myPanel.add(new JLabel("Message: ", SwingConstants.LEFT));
        myPanel.add(new JTextField(message));

        return myPanel;
    }

    @Override
    public void parseSettingsGUI(JPanel panel) {
        message = ((JTextField) panel.getComponent(1)).getText();
    }

    //GETTER & SETTER
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        super.setPanel(getMyPanel());
    }


}
