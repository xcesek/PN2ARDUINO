package org.pneditor.editor.actions.arduino;


import org.pneditor.arduino.component.device.Diode;
import org.pneditor.arduino.component.device.IDevice;
import org.pneditor.arduino.component.device.Potentiometer;
import org.pneditor.arduino.component.pin.ArduinoPin;
import org.pneditor.arduino.component.pin.ArduinoSupportedFunction;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.arduino.manager.ArduinoNodeExtension;
import org.pneditor.editor.Root;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.Place;
import org.pneditor.petrinet.Transition;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AssociateNodeWithArduinoPinAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private JCheckBox extEnabledCheckBox;
    private JComboBox pinCombo;
    private JComboBox supportedFunctionsCombo;
    private JComboBox devicesCombo;
    private JButton setupDeviceButton;

    public AssociateNodeWithArduinoPinAction(Root root) {
        this.root = root;
        String name = "Associate with Arduino";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/code.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);

        extEnabledCheckBox = new JCheckBox("Enable association");
        pinCombo = new JComboBox(ArduinoPin.values());
        supportedFunctionsCombo = new JComboBox(ArduinoSupportedFunction.values());
        devicesCombo = new JComboBox(getAllDevices().toArray());
        setupDeviceButton = new JButton("Setup connected device");
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() != null
                && (root.getClickedElement() instanceof Place || root.getClickedElement() instanceof Transition)) {
            Node clickedNode = (Node) root.getClickedElement();

            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(getPanel(clickedNode));
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Associate PN Node with Arduino Pin");
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);


            //save user input
            if (JOptionPane.YES_OPTION == (Integer) optionPane.getValue()) {
                ArduinoNodeExtension arduinoNodeExtension;
                if (extEnabledCheckBox.getModel().isSelected()) {
                    ArduinoPin pin = (ArduinoPin) pinCombo.getSelectedItem();
                    ArduinoSupportedFunction selectedFunction = (ArduinoSupportedFunction) supportedFunctionsCombo.getSelectedItem();
                    arduinoNodeExtension = new ArduinoNodeExtension(pin, selectedFunction);
                } else {
                    arduinoNodeExtension = new ArduinoNodeExtension();  // won't be enabled
                }

                if (clickedNode instanceof Place) {
                    ((Place) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                } else if (clickedNode instanceof Transition) {
                    ((Transition) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                }
            }
        }
    }

    public Object[] getPanel(Node clickedNode) {
        Object[] objects = new Object[10];

        // select values to combo according to stored data
        ArduinoNodeExtension extension = null;
        if (clickedNode instanceof Place) {
            extension = ((Place) clickedNode).getArduinoNodeExtension();
        } else if (clickedNode instanceof Transition) {
            extension = ((Transition) clickedNode).getArduinoNodeExtension();
        }

        if (!extension.isEnabled()) {   //defaults
            extension = new ArduinoNodeExtension(ArduinoPin.D2, ArduinoSupportedFunction.DIGITAL_OUT);
            extEnabledCheckBox.getModel().setSelected(false);
            pinCombo.setEnabled(false);
            supportedFunctionsCombo.setEnabled(false);
            devicesCombo.setEnabled(false);
            setupDeviceButton.setEnabled(false);
        } else {
            extEnabledCheckBox.getModel().setSelected(true);
            pinCombo.setEnabled(true);
            supportedFunctionsCombo.setEnabled(true);
            devicesCombo.setEnabled(true);
            setupDeviceButton.setEnabled(true);
        }

        pinCombo.setSelectedItem(extension.getPin());
        supportedFunctionsCombo.setModel(new DefaultComboBoxModel(extension.getPin().getSupportedFunctions()));
        supportedFunctionsCombo.setSelectedItem(extension.getFunction());


        objects[0] = extEnabledCheckBox;
        objects[1] = new JLabel("Choose Arduino Pin: ");
        objects[2] = pinCombo;
        objects[3] = Box.createVerticalStrut(5);
        objects[4] = new JSeparator(JSeparator.HORIZONTAL);

        objects[5] = new JLabel("Choose Function: ");
        objects[6] = supportedFunctionsCombo;


        objects[7] = new JLabel("Choose Device: ");
        objects[8] = devicesCombo;
        objects[9] = setupDeviceButton;

        extEnabledCheckBox.addActionListener(e -> {
            if (((AbstractButton) e.getSource()).getModel().isSelected()) {
                pinCombo.setEnabled(true);
                supportedFunctionsCombo.setEnabled(true);
                devicesCombo.setEnabled(true);
                setupDeviceButton.setEnabled(true);
            } else {
                pinCombo.setEnabled(false);
                supportedFunctionsCombo.setEnabled(false);
                devicesCombo.setEnabled(false);
                setupDeviceButton.setEnabled(false);
            }
        });

        pinCombo.addActionListener(e -> {
            ArduinoSupportedFunction[] supportedFunctions = ((ArduinoPin) pinCombo.getSelectedItem()).getSupportedFunctions();
            supportedFunctionsCombo.setModel(new DefaultComboBoxModel(supportedFunctions));

            actualizeDevicesCombo();
        });

        supportedFunctionsCombo.addActionListener(e -> {
            actualizeDevicesCombo();
        });

        setupDeviceButton.addActionListener(e -> {
            if (supportedFunctionsCombo.getModel().getSize() != 0) {    //?
                System.out.println("fadfafa");  // open new window
            }
        });

        return objects;
    }

    private void actualizeDevicesCombo() {
        List<IDevice> filteredDevices = getAllDevices().stream()
                .filter(d -> d.getSupportedFunction() == supportedFunctionsCombo.getSelectedItem())
                .collect(Collectors.toList());

        Object supportedDevices[] = filteredDevices.toArray();

        devicesCombo.setModel(new DefaultComboBoxModel(supportedDevices));
    }

    private List<IDevice> getAllDevices() {
        List<IDevice> devices = new ArrayList<>();
        devices.add(new Potentiometer());
        devices.add(new Diode());

        return devices;

    }
}