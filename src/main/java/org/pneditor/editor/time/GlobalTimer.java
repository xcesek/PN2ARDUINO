package org.pneditor.editor.time;

import javax.swing.SwingUtilities;

/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class GlobalTimer {

    TimingPolicyType type;
    boolean running;
    int seconds;

    public GlobalTimer() {
        running = false;
        seconds = 0;
    }

    public void setType(TimingPolicyType type) {
        this.type = type;
    }

    public TimingPolicyType getType() {
        return type;
    }

    public String getFormattedTime() {
        int hours = (int) (seconds / 60 / 60) % 24;
        int minutes = (int) (seconds / 60) % 60;
        int sec = (int) seconds % 60;

        return hours + " : " + minutes + " : " + sec;
    }

    public void start() {
        running = true;

        new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                while (running) {
                    long time = System.currentTimeMillis() - start;
                    final long sec = time / 1000;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            seconds = (int) sec;
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    public void stop() {
        running = false;
        seconds = 0;
    }
}
