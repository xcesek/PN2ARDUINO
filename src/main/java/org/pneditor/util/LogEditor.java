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
    private JScrollPane scrollPanelPneditor, scrollPanelArduino;
    private JTextArea textAreaPneditorLog, textAreaArduinoLog;
    private JPanel topPanel, buttonBar;
    public JButton pneditorButton, arduinoButton;
    private JLabel title;
    private Frame parentFrame;
    private final static String newline = "\n";

    public enum logType {
        ARDUINO, PNEDITOR
    }
    private logType textAreaType;

    public LogEditor(String title, Frame parent) {
        this.parentFrame = parent;
        textAreaPneditorLog = new JTextArea(5, 20);
        textAreaArduinoLog = new JTextArea(5, 20);

        textAreaPneditorLog.setEditable(false);
        textAreaArduinoLog.setEditable(false);

        setLayout(new BorderLayout());

        scrollPanelPneditor = new JScrollPane(textAreaPneditorLog);
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

        this.add(scrollPanelArduino, BorderLayout.CENTER);
        this.add(scrollPanelPneditor, BorderLayout.CENTER);

        pneditorButton.addActionListener(this);
        arduinoButton.addActionListener(this);
    }

    public void log(String logMessage, logType textAreaType) {

        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();

        switch(textAreaType){
            case ARDUINO:
                textAreaArduinoLog.append("[" + s.format(d) + "] " + logMessage + newline);
                textAreaArduinoLog.setCaretPosition(textAreaArduinoLog.getDocument().getLength());
                break;
            case PNEDITOR:
                textAreaPneditorLog.append("[" + s.format(d) + "] " + logMessage + newline);
                textAreaPneditorLog.setCaretPosition(textAreaPneditorLog.getDocument().getLength());
                break;
            default:
                System.out.println("LogType not found!");
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {

        this.revalidate();
        if (e.getSource().equals(pneditorButton)) {
            this.remove(scrollPanelArduino);
            this.add(scrollPanelPneditor, BorderLayout.CENTER);
            this.log("test log message for PNeditor",textAreaType.PNEDITOR);
            this.repaint();
        } else if (e.getSource().equals(arduinoButton)) {
            this.remove(scrollPanelPneditor);
            this.add(scrollPanelArduino, BorderLayout.CENTER);
            this.log("test log message for arduino board",textAreaType.ARDUINO);
            this.repaint();
        }
    }

    class SmallButton extends JButton {

        public SmallButton() {
            setMargin(new Insets(0, 0, 0, 0));
        }

    }

}
