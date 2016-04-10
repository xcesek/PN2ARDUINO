package org.pneditor.util;

import org.pneditor.editor.actions.arduino.ToggleComReaderAction;

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
    private JPanel topPanel, buttonBar, bottomButtonPanel;
    public JButton pneditorButton, arduinoButton, clearButton;
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

        Font font = new Font("Verdana", Font.BOLD, 12);
        textAreaPneditorLog.setFont(font);
        textAreaPneditorLog.setForeground(Color.DARK_GRAY);
        textAreaArduinoLog.setFont(font);
        textAreaArduinoLog.setForeground(Color.DARK_GRAY);

        textAreaPneditorLog.setEditable(false);
        textAreaArduinoLog.setEditable(false);

        setLayout(new BorderLayout());

        scrollPanelPneditor = new JScrollPane(textAreaPneditorLog);
        scrollPanelArduino = new JScrollPane(textAreaArduinoLog);

        buttonBar = new JPanel(new GridLayout(1, 3));

        this.title = new JLabel(title);
        this.title.setFont(new Font("Verdana", Font.PLAIN, 14));

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

        clearButton = new JButton();
        clearButton.setText("CLEAR");

        bottomButtonPanel = new JPanel(new BorderLayout());
        bottomButtonPanel.add(clearButton, BorderLayout.WEST);

        this.add(bottomButtonPanel, BorderLayout.SOUTH);

        pneditorButton.addActionListener(this);
        arduinoButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    public void log(String logMessage, logType textAreaType) {

        this.revalidate();
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();

        switch(textAreaType){
            case ARDUINO:
                arduinoButton.doClick();
                textAreaArduinoLog.append(logMessage);
                textAreaArduinoLog.setCaretPosition(textAreaArduinoLog.getDocument().getLength());
                break;
            case PNEDITOR:
                pneditorButton.doClick();
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
            arduinoButton.setBackground(Color.white);
            pneditorButton.setBackground(Color.blue);
            this.repaint();
        } else if (e.getSource().equals(arduinoButton)) {
            this.remove(scrollPanelPneditor);
            this.add(scrollPanelArduino, BorderLayout.CENTER);
            pneditorButton.setBackground(Color.white);
            arduinoButton.setBackground(Color.blue);
            this.repaint();
        } else if(e.getSource().equals(clearButton)){
            textAreaArduinoLog.setText(null);
            textAreaPneditorLog.setText(null);
            this.repaint();
        }
    }

    class SmallButton extends JButton {

        public SmallButton() {
            setMargin(new Insets(0, 0, 0, 0));
        }

    }

}
