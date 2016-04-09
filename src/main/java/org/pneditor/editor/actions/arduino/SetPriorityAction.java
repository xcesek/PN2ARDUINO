package org.pneditor.editor.actions.arduino;

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
public class SetPriorityAction extends AbstractAction {

    private Root root;

    public SetPriorityAction(Root root) {
        this.root = root;
        String name = "Set priority";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("icons/priority.png"));
        putValue(SHORT_DESCRIPTION, name);
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (root.getClickedElement() != null
                && root.getClickedElement() instanceof Transition) {
            Transition transition = (Transition) root.getClickedElement();

            Integer priority = transition.getPriority();
            if (priority == null) {
                priority = 0;
            }

            JTextField priorityField = new JTextField(priority.toString());
            Object[] message = {
                    "Priority:", priorityField,
            };

            int option = JOptionPane.showConfirmDialog(root.getParentFrame(), message, "Set priority", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                int newPriority;
                try {
                    newPriority = Integer.parseInt(priorityField.getText());
                } catch (NumberFormatException ex) {
                    newPriority = 0;
                }
                transition.setPriority(newPriority);
            }
        }
    }
}
