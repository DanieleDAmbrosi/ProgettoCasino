package com.casino.comm.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RemindTask extends TimerTask {

    public int time;
    public boolean isRunning;
    // public List<TimeChanged> listeners;
    public Thread mainThread;

    public RemindTask(int time, Thread mainThread) {
        this.time = time;
        this.isRunning = true;
        this.mainThread = mainThread;
        // listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        if (time > 0) {
            /*
             * for (TimeChanged listener : listeners) {
             * listener.onChangedTime(time);
             * }
             */
            System.out.println("Time remaining " + time + " seconds");
            time--;
        } else {
            System.out.println("Time's up");
            mainThread.stop();
            isRunning = false;
            //System.exit(0);
        }
    }

    private void displaysTimeRemaining() {
        System.out.println("Time reamining: " + time);
    }

    /*
     * public void addTimeChangedListener(TimeChanged listener) {
     * listeners.add(listener);
     * }
     */

}
