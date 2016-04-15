package org.pneditor.editor.time;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.pneditor.editor.PNEditor;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Transition;

public class SimpleTimer {

    private long seconds;
    private long elapsedSeconds;
    // Timer timer;
    Timer timer2;
    boolean isActive;
    private Marking marking;
    private Transition transition;


    private class SecondsCounter extends TimerTask {

        @Override
        public void run() {
            PNEditor.getRoot().repaintCanvas();
            elapsedSeconds--;
            transition.setLabel(String.valueOf(elapsedSeconds));
            if (elapsedSeconds < 1) {
                marking.firePhase2(transition);
                isActive = false;
                PNEditor.getRoot().repaintCanvas();
                cancel();
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

        timer2 = new Timer();
        isActive = true;

        if(marking.firePhase1(transition)){
            timer2.scheduleAtFixedRate(new SecondsCounter(), 0, 1000);
        }
    }

    public void cancel() {
        isActive = false;
        timer2.cancel();
    }

    public void resetTimer() {
        if (isActive) {
            cancel();
        }
        elapsedSeconds = seconds;
    }

    public boolean isActive() {
        return isActive;
    }

    private int determineDelaySeconds(int earliestFiringTime, int latestFiringTime) {
        if (earliestFiringTime == latestFiringTime) { // deterministic
            return earliestFiringTime;
        }

        Random randomGenerator = new Random();
        return randomGenerator.nextInt(latestFiringTime - earliestFiringTime) + earliestFiringTime;
    }
}
