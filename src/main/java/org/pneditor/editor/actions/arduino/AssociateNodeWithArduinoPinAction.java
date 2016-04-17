package org.pneditor.editor.actions.arduino;


import org.pneditor.arduino.component.pin.ArduinoPin;
import org.pneditor.arduino.component.pin.ArduinoSupportedFunction;
import org.pneditor.arduino.manager.ArduinoNodeExtension;
import org.pneditor.editor.Root;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.Place;
import org.pneditor.petrinet.Transition;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;


public class AssociateNodeWithArduinoPinAction extends AbstractAction {

    private Root root;
    EnumSet<ArduinoPin> enablesPins = EnumSet.allOf(ArduinoPin.class);

    public AssociateNodeWithArduinoPinAction(Root root) {
        this.root = root;
        String name = "Associate with Arduino";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/code.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);

    }

    public void actionPerformed(ActionEvent evt) {
        if (root.getClickedElement() != null
                && (root.getClickedElement() instanceof Place || root.getClickedElement() instanceof Transition)) {
            Node clickedNode = (Node) root.getClickedElement();

            JCheckBox extEnabledCheckBox = new JCheckBox("Enable association");
            JComboBox pinCombo = new JComboBox(enablesPins.toArray());
            JComboBox supportedFunctionsCombo = new JComboBox(ArduinoSupportedFunction.values());
            JCheckBox withDelayCheckBox = new JCheckBox("Apply delay");
            JTextField thresholdHighTf = new JTextField();
            JTextField thresholdLowTf = new JTextField();
            JCheckBox inverseLogicCheckBox = new JCheckBox("Inverse logic");


//            ====================================================================================
            List<Object> objects = new ArrayList<>();


            // select values to combo according to stored data
            ArduinoNodeExtension extension = null;
            if (clickedNode instanceof Place) {
                extension = ((Place) clickedNode).getArduinoNodeExtension();
            } else if (clickedNode instanceof Transition) {
                extension = ((Transition) clickedNode).getArduinoNodeExtension();
            }

            if (extension == null || !extension.isEnabled()) {   //defaults
                extension = new ArduinoNodeExtension(ArduinoPin.D2, ArduinoSupportedFunction.DIGITAL_OUT);
                extension.setEnabled(false);

                pinCombo.setEnabled(false);
                supportedFunctionsCombo.setEnabled(false);
                withDelayCheckBox.setEnabled(false);
                inverseLogicCheckBox.setEnabled(false);
                thresholdLowTf.setEnabled(false);
                thresholdHighTf.setEnabled(false);
            } else {
                pinCombo.setEnabled(true);
                supportedFunctionsCombo.setEnabled(true);
                withDelayCheckBox.setEnabled(true);
                inverseLogicCheckBox.setEnabled(true);
                thresholdLowTf.setEnabled(true);
                thresholdHighTf.setEnabled(true);
            }

            // init with stored values
            extEnabledCheckBox.setSelected(extension.isEnabled());
            pinCombo.setSelectedItem(extension.getPin());
            supportedFunctionsCombo.setModel(new DefaultComboBoxModel(extension.getPin().getSupportedFunctions()));
            supportedFunctionsCombo.setSelectedItem(extension.getFunction());
            withDelayCheckBox.setSelected(extension.isWithDelay());
            inverseLogicCheckBox.setSelected(extension.getInverseLogic());
            thresholdLowTf.setText(String.valueOf(extension.getThresholdRangeLow()));
            thresholdHighTf.setText(String.valueOf((extension.getThresholdRangeHigh())));


            objects.add(extEnabledCheckBox);
            objects.add(new JLabel("Choose Arduino Pin: "));
            objects.add(pinCombo);

            objects.add(new JLabel("Choose Function: "));
            objects.add(supportedFunctionsCombo);
            objects.add(Box.createVerticalStrut(5));
            objects.add(new JSeparator(JSeparator.HORIZONTAL));

            if (clickedNode instanceof Transition) {
                objects.add(withDelayCheckBox);
                objects.add(Box.createVerticalStrut(5));
                objects.add(new JSeparator(JSeparator.HORIZONTAL));
            }

            objects.add(new JLabel("Threshold range low:"));
            objects.add(thresholdLowTf);

            objects.add(new JLabel("Threshold range high:"));
            objects.add(thresholdHighTf);

            objects.add(inverseLogicCheckBox);

            extEnabledCheckBox.addActionListener(e -> {
                        if (((AbstractButton) e.getSource()).getModel().isSelected()) {
                            pinCombo.setEnabled(true);
                            supportedFunctionsCombo.setEnabled(true);
                            withDelayCheckBox.setEnabled(true);
                            inverseLogicCheckBox.setEnabled(true);
                            thresholdLowTf.setEnabled(true);
                            thresholdHighTf.setEnabled(true);
                        } else {
                            pinCombo.setEnabled(false);
                            supportedFunctionsCombo.setEnabled(false);
                            withDelayCheckBox.setEnabled(false);
                            inverseLogicCheckBox.setEnabled(false);
                            thresholdLowTf.setEnabled(false);
                            thresholdHighTf.setEnabled(false);
                        }
                    }
            );

            pinCombo.addActionListener(e -> {
                        ArduinoSupportedFunction[] supportedFunctions = ((ArduinoPin) pinCombo.getSelectedItem()).getSupportedFunctions();
                        supportedFunctionsCombo.setModel(new DefaultComboBoxModel(supportedFunctions));
                    }
            );

//            ==========================================


            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(objects.toArray());
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Associate PN Node with Arduino Pin");
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);


            //save user input
            if (JOptionPane.YES_OPTION == (Integer) optionPane.getValue()) {
                ArduinoNodeExtension arduinoNodeExtension;
                if (extEnabledCheckBox.isSelected()) {
                    ArduinoPin pin = (ArduinoPin) pinCombo.getSelectedItem();

                    // remove only selected pin
                    //enablesPins.remove(pin);

                    ArduinoSupportedFunction selectedFunction = (ArduinoSupportedFunction) supportedFunctionsCombo.getSelectedItem();

                    if(selectedFunction.equals(ArduinoSupportedFunction.LED_DISPLAY)){

                        //delete pins reserved for 8 segment led display (D2-D8 needed)
                        ArduinoPin[] pinsForLed = new ArduinoPin[]{ ArduinoPin.D2, ArduinoPin.D3, ArduinoPin.D4, ArduinoPin.D5, ArduinoPin.D6, ArduinoPin.D7, ArduinoPin.D8 };
                        for(ArduinoPin i : pinsForLed){
                            //enablesPins.remove(i);
                        }
                    }
                    arduinoNodeExtension = new ArduinoNodeExtension(pin, selectedFunction);
                    arduinoNodeExtension.setWithDelay(withDelayCheckBox.isSelected());
                    arduinoNodeExtension.setInverseLogic(inverseLogicCheckBox.isSelected());

                    try {
                        int high = Integer.parseInt(thresholdHighTf.getText());
                        int low = Integer.parseInt(thresholdLowTf.getText());
                        arduinoNodeExtension.setThresholdRangeHigh(high);
                        arduinoNodeExtension.setThresholdRangeLow(low);
                    } catch (NumberFormatException ignore) {
                        arduinoNodeExtension.setThresholdRangeHigh(-1);
                        arduinoNodeExtension.setThresholdRangeLow(-1);
                        JOptionPane.showMessageDialog(root.getParentFrame(), "Wrong threshold value", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    arduinoNodeExtension = new ArduinoNodeExtension();  // won't be enabled
                    arduinoNodeExtension.setEnabled(false);
                }

                if (clickedNode instanceof Place) {
                    ((Place) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                } else if (clickedNode instanceof Transition) {
                    ((Transition) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                }
            }
        }
    }

}