package org.pneditor.editor.actions.arduino;

import org.firmata4j.Pin;
import org.pneditor.arduino.components.ArduinoComponent;
import org.pneditor.arduino.components.ArduinoComponentType;
import org.pneditor.arduino.components.ArduinoComponentSettings;
import org.pneditor.editor.Root;
import org.pneditor.editor.commands.arduino.SetArduinoComponentCommand;
import org.pneditor.petrinet.Node;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class AddArduinoComponentAction extends AbstractAction {

    private Root root;
    //COMPONENT SETTINGS
    private int pinNumber;
    private ArduinoComponentType arduinoComponentType;
    private ArduinoComponentSettings arduinoComponentSettings;

    private Node clickedNode;


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
            clickedNode = (Node) root.getClickedElement();

            if (requestComponentSettings()) {
                //TODO prerobit cez comand
                ArduinoComponent arduinoComponent = ArduinoComponent.componentFactory(pinNumber, arduinoComponentType, arduinoComponentSettings, root.getArduinoManager(), clickedNode);
                root.getUndoManager().executeCommand(new SetArduinoComponentCommand(clickedNode, arduinoComponent));
            }

        }

    }

    public boolean requestComponentSettings() {

        List<Object> objectList = new ArrayList<>();

        GridLayout gridLayout = new GridLayout(0, 2);

        //select arduino component
        JPanel arduinoComponentComboBoxPanel = new JPanel(gridLayout);
        final JComboBox arduinoComponentComboBox = new JComboBox(ArduinoComponentType.values());
        // * if node already has arduino component - load from clickedNode
        if(clickedNode.hasArduinoComponent()) {
            arduinoComponentComboBox.setSelectedItem(clickedNode.getArduinoComponent().getType());
        } else {
            arduinoComponentComboBox.setSelectedItem(ArduinoComponentType.OUTPUT);
        }

        arduinoComponentComboBoxPanel.add(new JLabel("Arduino Component: ", SwingConstants.LEFT));
        arduinoComponentComboBoxPanel.add(arduinoComponentComboBox);

        objectList.add(arduinoComponentComboBoxPanel);

        //vertical spacer
        objectList.add(Box.createVerticalStrut(5));
        //separator
        objectList.add(new JSeparator(JSeparator.HORIZONTAL));
        //vertical spacer
        objectList.add(Box.createVerticalStrut(5));

        //pin
        final JPanel pinPanel = new JPanel(gridLayout);
        final JComboBox pinComboBox = new JComboBox();

        Map<ArduinoComponentType, List<Byte>> modelMap = setDefaultComboBoxModels();

        //*default
        Object[] modeArray = modelMap.get(arduinoComponentComboBox.getSelectedItem()).toArray();
        Arrays.sort(modeArray);
        pinComboBox.setModel(new DefaultComboBoxModel(modeArray));
        // * if node already has arduino component - load from clickedNode
        if(clickedNode.hasArduinoComponent()) {
            pinComboBox.setSelectedItem(((Integer)clickedNode.getArduinoComponent().getPin()).byteValue());
        } else{
            pinComboBox.setSelectedIndex(0);
        }

        pinPanel.add(new JLabel("Pin: ", SwingConstants.LEFT));
        pinPanel.add(pinComboBox);

        objectList.add(pinPanel);

        JPanel separatorPanel = new JPanel(new GridLayout(0,1));
        separatorPanel.add(Box.createVerticalStrut(5));
        separatorPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        separatorPanel.add(Box.createVerticalStrut(5));

        objectList.add(separatorPanel);

        //custom settings
        // * if node already has arduino component - load from clickedNode
        if(clickedNode.hasArduinoComponent()) {
            arduinoComponentSettings = clickedNode.getArduinoComponent().getSettings();
        } else {
            arduinoComponentSettings = ArduinoComponentSettings.settingsFactory((ArduinoComponentType) arduinoComponentComboBox.getSelectedItem(), clickedNode);
        }
        JPanel customSettings = arduinoComponentSettings.getSettingsGui();

        objectList.add(customSettings);

        arduinoComponentComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clickedNode.hasArduinoComponent() && arduinoComponentComboBox.getSelectedItem() == clickedNode.getArduinoComponent().getType()) {
                    // pin
                    Object[] tmpModeArray = modelMap.get(arduinoComponentComboBox.getSelectedItem()).toArray();
                    Arrays.sort(tmpModeArray);
                    pinComboBox.setModel(new DefaultComboBoxModel(tmpModeArray));

                    pinPanel.remove(pinComboBox); //remove old
                    pinComboBox.setSelectedItem(((Integer)clickedNode.getArduinoComponent().getPin()).byteValue());
                    pinPanel.add(pinComboBox);

                    objectList.remove(pinPanel); //remove old
                    objectList.add(pinPanel);

                    objectList.remove(separatorPanel); //remove old
                    objectList.add(separatorPanel);

                    //custom settings
                    objectList.remove(arduinoComponentSettings.getSettingsGui()); //remove old
                    arduinoComponentSettings = clickedNode.getArduinoComponent().getSettings();
                    objectList.add(arduinoComponentSettings.getSettingsGui());
                } else {
                    // pin
                    Object[] tmpModeArray = modelMap.get(arduinoComponentComboBox.getSelectedItem()).toArray();
                    Arrays.sort(tmpModeArray);
                    pinComboBox.setModel(new DefaultComboBoxModel(tmpModeArray));

                    pinPanel.remove(pinComboBox); //remove old
                    pinComboBox.setSelectedIndex(0);
                    pinPanel.add(pinComboBox);

                    objectList.remove(pinPanel); //remove old
                    objectList.add(pinPanel);

                    objectList.remove(separatorPanel); //remove old
                    objectList.add(separatorPanel);

                    //custom settings
                    objectList.remove(arduinoComponentSettings.getSettingsGui()); //remove old
                    arduinoComponentSettings = ArduinoComponentSettings.settingsFactory((ArduinoComponentType) arduinoComponentComboBox.getSelectedItem(), clickedNode);
                    objectList.add(arduinoComponentSettings.getSettingsGui());
                }
            }
        });

        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(objectList.toArray());
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

        JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Arduino Component");
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        //if OK
        int value = (optionPane.getValue() != null) ? ((Integer) optionPane.getValue()).intValue() : JOptionPane.CANCEL_OPTION;
        if (value == JOptionPane.YES_OPTION) {
            arduinoComponentType = (ArduinoComponentType) arduinoComponentComboBox.getSelectedItem();
            pinNumber = ((Byte) pinComboBox.getSelectedItem()).intValue();
            arduinoComponentSettings.parseSettingsGUI(customSettings);
            return true;
        }
        return false;


    }

    private Map<ArduinoComponentType, List<Byte>> setDefaultComboBoxModels() {
        Map<ArduinoComponentType, List<Byte>> modelMap = new HashMap<>();

        for (ArduinoComponentType type : ArduinoComponentType.values()) {
            modelMap.put(type, new ArrayList<>());
        }

        for (Pin pin : root.getArduinoManager().getDevice().getPins()) {
            for (Pin.Mode mode : pin.getSupportedModes()) {
                try {
                    ArduinoComponentType type = ArduinoComponentType.valueOf(mode.name());
                    modelMap.get(type).add(pin.getIndex());
                } catch (IllegalArgumentException e) {
                    System.out.println("Nepodporovany mod" + mode.name());
                } catch (Exception e) {
                    System.out.println("Nobody knows what happend");
                }
            }
        }


        return modelMap;
    }

    private Map<ArduinoComponentType, List<Object>> setCustomSettings() {
        Map<ArduinoComponentType, List<Object>> objectList = new HashMap<>();

        for (ArduinoComponentType type : ArduinoComponentType.values()) {
            objectList.put(type, new ArrayList<>());
        }


        return objectList;
    }
}