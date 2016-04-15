package org.pneditor.editor.actions;

import org.pneditor.editor.Root;
import org.pneditor.editor.time.GlobalTimer;
import org.pneditor.util.GraphicsTools;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class AutoFiringAction extends AbstractAction {

    private Root root;

    public AutoFiringAction(Root root) {
        this.root = root;
        String name = "Automatic Firing Mode";
        putValue(NAME, name);
        putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/play16.png"));
        putValue(SHORT_DESCRIPTION, name);
//		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
//		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("R"));
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        root.selectTool_Token();
        if(!root.getAutoFiring().isRunning()) {
            putValue(SMALL_ICON,  GraphicsTools.getIcon("pneditor/stop16.png"));
            root.getAutoFiring().start();
        } else {
            putValue(SMALL_ICON, GraphicsTools.getIcon("pneditor/play16.png"));
            root.getAutoFiring().stop();
        }
    }
}
