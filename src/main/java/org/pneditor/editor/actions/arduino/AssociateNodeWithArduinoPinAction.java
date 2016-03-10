package org.pneditor.editor.actions.arduino;


import org.pneditor.arduino.component.ArduinoPin;
import org.pneditor.arduino.component.ArduinoSupportedFunction;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.arduino.manager.ArduinoNodeExtension;
import org.pneditor.editor.Root;
import org.pneditor.petrinet.Node;
import org.pneditor.petrinet.Place;
import org.pneditor.petrinet.Transition;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class AssociateNodeWithArduinoPinAction extends AbstractAction {

    private Root root;
    private ArduinoManager arduinoManager;

    private JComboBox pinCombo;
    private JComboBox supportedFunctionsCombo;

    public AssociateNodeWithArduinoPinAction(Root root) {
        this.root = root;
        String name = "Add Arduino Component";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/uploadCode.gif"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);

        pinCombo = new JComboBox(ArduinoPin.values());
        supportedFunctionsCombo = new JComboBox(ArduinoSupportedFunction.values());
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
                ArduinoPin pin = (ArduinoPin) pinCombo.getSelectedItem();
                ArduinoSupportedFunction selectedFunction = (ArduinoSupportedFunction) supportedFunctionsCombo.getSelectedItem();

                ArduinoNodeExtension arduinoNodeExtension = new ArduinoNodeExtension(pin, selectedFunction);
                if (clickedNode instanceof Place) {
                    ((Place) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                } else if (clickedNode instanceof Transition) {
                    ((Transition) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                }
            }
        }
    }

    public Object[] getPanel(Node clickedNode) {
        Object[] objects = new Object[6];

        // select values to combo according to stored data
        ArduinoNodeExtension extension = null;
        if (clickedNode instanceof Place) {
            extension = ((Place) clickedNode).getArduinoNodeExtension();
        } else if (clickedNode instanceof Transition) {
            extension = ((Transition) clickedNode).getArduinoNodeExtension();
        }

        if (extension == null) {
            // when clicked for the first time
            extension = new ArduinoNodeExtension(ArduinoPin.D2, ArduinoSupportedFunction.DIGITAL_OUT);
        }
        pinCombo.setSelectedItem(extension.getPin());
        supportedFunctionsCombo.setModel(new DefaultComboBoxModel(extension.getPin().getSupportedFunctions()));
        supportedFunctionsCombo.setSelectedItem(extension.getFunction());

        objects[0] = new JLabel("Choose Arduino Pin: ");
        objects[1] = pinCombo;
        objects[2] = Box.createVerticalStrut(5);
        objects[3] = new JSeparator(JSeparator.HORIZONTAL);

        objects[4] = new JLabel("Choose Function: ");
        objects[5] = supportedFunctionsCombo;

        pinCombo.addActionListener(e -> {
            ArduinoSupportedFunction[] supportedFunctions = ((ArduinoPin) pinCombo.getSelectedItem()).getSupportedFunctions();
            supportedFunctionsCombo.setModel(new DefaultComboBoxModel(supportedFunctions));
        });

        return objects;
    }
}