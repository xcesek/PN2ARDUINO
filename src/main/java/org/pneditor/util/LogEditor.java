package org.pneditor.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laci on 07.03.2016.
 */
public class LogEditor extends JPanel implements ActionListener {
    private JScrollPane scrollPanelPNeditor, scrollPanelArduino;
    private JTextArea textAreaPNeditorLog, textAreaArduinoLog;
    private JPanel topPanel, buttonBar;
    public JButton pneditorButton, arduinoButton;
    private JLabel title;
    private Frame parent;
    private final static String newline = "\n";

    public LogEditor(String title, Frame parent) {
        this.parent = parent;
        textAreaPNeditorLog = new JTextArea(5, 20);
        textAreaArduinoLog = new JTextArea(5, 20);

        textAreaPNeditorLog.setEditable(false);
        textAreaArduinoLog.setEditable(false);

        setLayout(new BorderLayout());

        scrollPanelPNeditor = new JScrollPane(textAreaPNeditorLog);
        scrollPanelArduino = new JScrollPane(textAreaArduinoLog);

        buttonBar = new JPanel(new GridLayout(1, 3));

        this.title = new JLabel(title);

        pneditorButton = new SmallButton();
        arduinoButton = new SmallButton();

        pneditorButton.setText("PNeditor");
        arduinoButton.setText("Arduino");

        pneditorButton.setToolTipText("PNeditor");
        arduinoButton.setToolTipText("Arduino board");

        buttonBar.add(pneditorButton);
        buttonBar.add(arduinoButton);

        topPanel = new JPanel(new BorderLayout());
        topPanel.add(this.title, BorderLayout.NORTH);
        topPanel.add(buttonBar, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPanelPNeditor, BorderLayout.CENTER);

        pneditorButton.addActionListener(this);
        arduinoButton.addActionListener(this);

        this.setEntry("test log message for PNeditor",textAreaPNeditorLog);
    }

    public void setEntry(String entry, JTextArea textarea) {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();

        textarea.append("[" + s.format(d) + "] " + entry + newline);
        textarea.setCaretPosition(textarea.getDocument().getLength());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(pneditorButton)) {
            this.remove(scrollPanelArduino);
            this.add(scrollPanelPNeditor, BorderLayout.CENTER);
        } else if (e.getSource().equals(arduinoButton)) {
            this.remove(scrollPanelPNeditor);
            this.add(scrollPanelArduino, BorderLayout.CENTER);
            this.setEntry("test log message for arduino board",textAreaArduinoLog);
        }
    }

    class SmallButton extends JButton {

        public SmallButton() {
            setMargin(new Insets(0, 0, 0, 0));
        }

    }

}
