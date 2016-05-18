package org.pneditor.editor.time;

import org.pneditor.editor.PNEditor;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Transition;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Alzbeta Cesekova
 * xbuckuliakova@stuba.sk
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class SimpleTimer {

    private long seconds;
    private long elapsedSeconds;
    private Timer timer;
    boolean isActive;
    private Marking marking;
    private Transition transition;
    private String oldLabel = "";


    private class SecondsCounter extends TimerTask {

        @Override
        public void run() {
            PNEditor.getRoot().repaintCanvas();
            elapsedSeconds--;
            transition.setLabel(String.valueOf(elapsedSeconds));
            if (elapsedSeconds <= 0) {
                cancel();
                setActive(false);
                transition.setLabel(oldLabel);
                elapsedSeconds = seconds;
                PNEditor.getRoot().repaintCanvas();

                marking.firePhase2(transition);
            }
        }
    }

    public SimpleTimer(int earliestFiringTime, int latestFiringTime) {
        seconds = determineDelaySeconds(earliestFiringTime, latestFiringTime);
        elapsedSeconds = seconds;
    }

    public void startTimer(Transition transition, Marking marking) {
        this.transition = transition;
        this.marking = marking;

        timer = new Timer();
        setActive(true);

        if (marking.firePhase1(transition)) {
            oldLabel = transition.getLabel();
            transition.setLabel(oldLabel);
            PNEditor.getRoot().repaintCanvas();

            timer.scheduleAtFixedRate(new SecondsCounter(), 1000, 1000);
        }
    }

    public void cancel() {
        setActive(false);
        timer.cancel();
    }

    public void resetTimer() {
        if (isActive()) {
            cancel();
        }
        elapsedSeconds = seconds;
    }

    public synchronized boolean isActive() {
        return isActive;
    }

    public synchronized void setActive(boolean active) {
        isActive = active;
    }

    private int determineDelaySeconds(int earliestFiringTime, int latestFiringTime) {
        if (earliestFiringTime == latestFiringTime) { // deterministic
            return earliestFiringTime;
        }

        Random randomGenerator = new Random();
        return randomGenerator.nextInt(latestFiringTime - earliestFiringTime) + earliestFiringTime;
    }

    public long getSeconds() {
        return seconds;
    }
}
