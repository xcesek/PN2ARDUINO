package org.pneditor.editor;

import org.pneditor.editor.commands.FireTransitionCommand;
import org.pneditor.petrinet.Marking;
import org.pneditor.petrinet.Transition;
import org.pneditor.util.LogEditor;

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
public class AutoFiring {

    private boolean running;
    private Timer timer;

    AutoFiring() {
        running = false;
    }

    public void start(){
        running = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new FiringTimer(), 0, 5000);
    }

    public void stop(){
        running = false;
        timer.cancel();
    }

    //GETTER & SETTER
    public boolean isRunning() {
        return running;
    }

    private class FiringTimer extends TimerTask {

        @Override
        public void run() {
            Transition transition = null;
            Marking initialMarking = PNEditor.getRoot().getCurrentMarking();
            try{
                transition = initialMarking.fireRandomTransition();
                PNEditor.getRoot().getUndoManager().executeCommand(new FireTransitionCommand(transition, initialMarking));
            } catch(RuntimeException e){
                //((RootPflow)PNEditor.getRoot()).getLogEditor().log("No transitions is enabled", LogEditor.logType.PNEDITOR);
            }
        }
    }
}
