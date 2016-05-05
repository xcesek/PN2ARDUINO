package org.pneditor.arduino.bridge;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.pneditor.util.LogEditor;

import java.io.UnsupportedEncodingException;

/**
 * Created by palo on 4/10/2016.
 */
public class ArduinoComReader implements SerialPortEventListener {
    private LogEditor logEditor;
    private SerialPort serialPort;

    public ArduinoComReader(LogEditor logEditor) {
        this.logEditor = logEditor;
    }

    public boolean init(String portNubmer) {
        try {
            close();    // try to close quietly
            serialPort = new SerialPort(portNubmer);
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);
            serialPort.addEventListener(this);
            return true;

        } catch (SerialPortException ex) {
            logEditor.log("Cannot establish connection to Arduino device", LogEditor.logType.PNEDITOR);
            return false;
        }
    }

    public boolean close() {
        try {
            if (serialPort != null) {
                serialPort.removeEventListener();
                serialPort.closePort();
                serialPort = null;
            }
            return true;
        } catch (SerialPortException e) {
            return false;
        }
    }

    public boolean isOpen() {
        return serialPort != null ? serialPort.isOpened() : false;
    }


    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {//If data is available
                try {
                    byte buffer[] = serialPort.readBytes();
                    logEditor.log(new String(buffer, "UTF-8"), LogEditor.logType.ARDUINO);
                } catch (SerialPortException | UnsupportedEncodingException ex) {
                    logEditor.log("Error occurred while reading data from Arduino", LogEditor.logType.PNEDITOR);
                }
        }
    }
}
