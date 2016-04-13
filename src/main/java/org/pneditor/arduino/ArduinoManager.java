package org.pneditor.arduino;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.pneditor.arduino.components.common.ArduinoComponentType;
import org.pneditor.editor.PNEditor;

import javax.swing.*;
import java.util.*;

/**
 * Created by Pavol Cesek on 2/27/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class ArduinoManager {

    private BoardSettings boardSettings;
    private IODevice device;

    private Map<ArduinoComponentType, List<Byte>> pinMap;
    private List<Byte> usedPins;

    // CONSTRUCTOR
    public ArduinoManager() {

        boardSettings = new BoardSettings();    // todo
        usedPins = new ArrayList<>();
    }



    public void initializePinMap() {
        Map<ArduinoComponentType, List<Byte>> modelMap = new HashMap<>();

        for (ArduinoComponentType type : ArduinoComponentType.values()) {
            modelMap.put(type, new ArrayList<>());
        }

        if(getDevice() == null) {
            JOptionPane.showMessageDialog(PNEditor.getRoot().getParentFrame(), "Please set Arduino board first.", "Arduino Board Error", JOptionPane.ERROR_MESSAGE);
        } else{
            for (Pin pin : getDevice().getPins()) {
                for (Pin.Mode mode : pin.getSupportedModes()) {
                    try {
                        ArduinoComponentType type = ArduinoComponentType.valueOf(mode.name());
                        modelMap.get(type).add(pin.getIndex());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nepodporovany mod" + mode.name());
                    } catch (Exception e) {
                        System.out.println("Nobody knows what happend");
                    }
                }
            }
        }
        pinMap = modelMap;
    }


    //GETTER & SETTER
    public IODevice getDevice() {
        return device;
    }
    public void setDevice(IODevice device) {
        this.device = device;
    }

    public void updateSettings(String port, String board) {
        boardSettings.setPort(port);
    }
    public BoardSettings getBoardSettings() {
        return boardSettings;
    }

    public Map<ArduinoComponentType, List<Byte>> getPinMap() {
        return pinMap;
    }

    public List<Byte> getUsedPins() {
        return usedPins;
    }

    public Object[] getUnusedPins(ArduinoComponentType type) {
        if(pinMap == null) {
            initializePinMap();
        }
        ArrayList<Byte> pinList = (ArrayList<Byte>) pinMap.get(type);
        pinList.removeAll(usedPins);

        Object[] pinArray = pinList.toArray();
        Arrays.sort(pinArray);

        return pinArray;
    }

}
