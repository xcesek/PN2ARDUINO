package org.pneditor.editor.actions.arduino;

import org.pneditor.arduino.time.TimingPolicyType;
import org.pneditor.editor.Root;
import org.pneditor.petrinet.Transition;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Pavol Cesek on 2/26/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class SetDelayAction extends AbstractAction {

    private Root root;

    public SetDelayAction(Root root) {
        this.root = root;
        String name = "Set delay";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/clock.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() != null
                && root.getClickedElement() instanceof Transition) {
            Transition transition = (Transition) root.getClickedElement();

            if (root.getArduinoManager().getTimingPolicyType() == TimingPolicyType.Stochastic) {
                Integer oldEarliestFiringTime = transition.getEarliestFiringTime();
                Integer oldLatestFiringTime = transition.getLatestFiringTime();
                String earliestFiringTimeValue = "";
                String latestFiringTimeValue = "";

                if (oldEarliestFiringTime != null) {
                    earliestFiringTimeValue = oldEarliestFiringTime.toString();
                }
                if (oldLatestFiringTime != null) {
                    latestFiringTimeValue = oldLatestFiringTime.toString();
                }

                JTextField earliestFiringTimeField = new JTextField(earliestFiringTimeValue);
                JTextField latestFiringTimeField = new JTextField(latestFiringTimeValue);
                Object[] message = {
                        "New earliest firing time (milliseconds):", earliestFiringTimeField,
                        "New latest firing time (milliseconds):", latestFiringTimeField
                };
                int option = JOptionPane.showConfirmDialog(root.getParentFrame(), message, "Set firing times (milliseconds)", JOptionPane.OK_CANCEL_OPTION);
                String newEarliestFiringTimeStr = "";
                String newLatestFiringTimeStr = "";
                if (option == JOptionPane.OK_OPTION) {
                    newEarliestFiringTimeStr = earliestFiringTimeField.getText();
                    newLatestFiringTimeStr = latestFiringTimeField.getText();
                } else {
                    return;
                }

                Integer newEarliestFiringTime;
                Integer newLatestFiringTime;
                try {
                    newEarliestFiringTime = Integer.parseInt(newEarliestFiringTimeStr);
                    newLatestFiringTime = Integer.parseInt(newLatestFiringTimeStr);
                } catch (NumberFormatException ex) {
                    newEarliestFiringTime = 0;
                    newLatestFiringTime = 0;
                }

                if (newEarliestFiringTime != null && !newEarliestFiringTimeStr.equals(transition.getEarliestFiringTime())
                        && newLatestFiringTime != null && !newLatestFiringTimeStr.equals(transition.getLatestFiringTime())
                        && newLatestFiringTime >= newEarliestFiringTime) {
                    transition.setEarliestFiringTime(newEarliestFiringTime);
                    transition.setLatestFiringTime(newLatestFiringTime);
//                    root.getUndoManager().executeCommand(new SetDelayCommand(transition, newEarliestFiringTime, newLatestFiringTime));
                } else {
                    //error
                    JOptionPane.showMessageDialog(root.getParentFrame(), "Firing times weren't changed!", "Error", JOptionPane.ERROR_MESSAGE);
                }


            } else {
                String newDelayStr = JOptionPane.showInputDialog(root.getParentFrame(), "New delay: (milliseconds)", transition.getEarliestFiringTime());

                Integer newDelay;
                try {
                    newDelay = Integer.parseInt(newDelayStr);
                } catch (NumberFormatException ex) {
                    newDelay = 0;
                }

                if (newDelayStr != null && !newDelayStr.equals(transition.getEarliestFiringTime())) {
                    transition.setEarliestFiringTime(newDelay);
                    transition.setLatestFiringTime(newDelay);
//                    root.getUndoManager().executeCommand(new SetDelayCommand(transition, newDelay, newDelay)); //set earliest and latest firing time to one value
                } else {
                    //error
                    JOptionPane.showMessageDialog(root.getParentFrame(), "Delay wasn't changed!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
    }
}
