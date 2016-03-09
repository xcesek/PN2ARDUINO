package org.pneditor.editor.actions.arduino;


import org.pneditor.arduino.component.ArduinoPinDirection;
import org.pneditor.arduino.component.ArduinoPinType;
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

    private JComboBox pinTypeCombo;
    private JComboBox pinNumberCombo;
    private JComboBox pinDirectionCombo;

    public AssociateNodeWithArduinoPinAction(Root root) {
        this.root = root;
        String name = "Add Arduino Component";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/uploadCode.gif"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);

        pinTypeCombo = new JComboBox(ArduinoPinType.values());
        pinNumberCombo = new JComboBox();
        pinDirectionCombo = new JComboBox(ArduinoPinDirection.values());
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() != null
                && root.getClickedElement() instanceof Node) {
            Node clickedNode = (Node) root.getClickedElement();

            final JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(getPanel(clickedNode));
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog(root.getParentFrame(), "Associate PN Node with Arduino Pin");
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);


            //set user values
            int value = ((Integer) optionPane.getValue()).intValue();
            if (value == JOptionPane.YES_OPTION) {
                ArduinoPinType pinType = (ArduinoPinType) pinTypeCombo.getSelectedItem();
                ArduinoPinDirection pinDirection = (ArduinoPinDirection) pinDirectionCombo.getSelectedItem();
                Integer pinNumber = (Integer) pinNumberCombo.getSelectedItem();

                ArduinoNodeExtension arduinoNodeExtension = new ArduinoNodeExtension(pinType, pinDirection, pinNumber);
                if (clickedNode instanceof Place) {
                    ((Place) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                } else if (clickedNode instanceof Transition) {
                    ((Transition) clickedNode).setArduinoNodeExtension(arduinoNodeExtension);
                }
            }

        }

    }

    public Object[] getPanel(Node clickedNode) {
        Object[] objects = new Object[7];

        // PIN models
        final DefaultComboBoxModel digitalPins = new DefaultComboBoxModel(new Integer[]{13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2});
        final DefaultComboBoxModel analogPins = new DefaultComboBoxModel(new Integer[]{5, 4, 3, 2, 1, 0});

        // select values to combo according to stored data
        // todo
        pinTypeCombo.setSelectedIndex(0);
        pinNumberCombo.setModel(digitalPins);
        pinNumberCombo.setSelectedIndex(0);

//        ArduinoNodeExtension arduinoNodeExtension;
//        if (clickedNode instanceof Place) {
//            arduinoNodeExtension = ((Place) clickedNode).getArduinoNodeExtension();
//        } else if (clickedNode instanceof Transition) {
//            arduinoNodeExtension = ((Transition) clickedNode).getArduinoNodeExtension();
//        } else {
//            arduinoNodeExtension = new ArduinoNodeExtension();
//        }
//
//        pinTypeCombo.setSelectedItem(arduinoNodeExtension.getPinType());
//        pinNumberCombo.setSelectedItem(arduinoNodeExtension.getPinNumber());
//        pinDirectionCombo.setSelectedItem(arduinoNodeExtension.getPinDirection());


        //  Arduino Pin Type
        JPanel pinTypeComboPanel = new JPanel();
        pinTypeComboPanel.add(new JLabel("Choose Arduino Component Type: "));
        pinTypeComboPanel.add(pinTypeCombo);
        objects[0] = pinTypeComboPanel;
        objects[1] = new JSeparator(JSeparator.HORIZONTAL);
        objects[2] = Box.createVerticalStrut(5);

        objects[3] = pinNumberCombo;
        objects[4] = new JSeparator(JSeparator.HORIZONTAL);

        JPanel pinDirectionComboPanel = new JPanel();
        pinDirectionComboPanel.add(new JLabel("Choose Direction: "));
        pinDirectionComboPanel.add(pinDirectionCombo);
        objects[5] = pinDirectionComboPanel;
        objects[6] = Box.createVerticalStrut(5);

        pinTypeCombo.addActionListener(e -> {
            if (ArduinoPinType.DIGITAL == pinTypeCombo.getSelectedItem()) {
                pinNumberCombo.setModel(digitalPins);
            } else {
                pinNumberCombo.setModel(analogPins);
            }
        });

        return objects;
    }
}