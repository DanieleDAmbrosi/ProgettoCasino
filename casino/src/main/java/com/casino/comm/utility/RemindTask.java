package com.casino.comm.utility;

import java.util.TimerTask;

public class RemindTask extends TimerTask {

    public int time;
    public boolean isRunning;

    public RemindTask(int time) {
        this.time = time;
        this.isRunning = true;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (time != 0) {
            displaysTimeRemaining();
            time--;

        } else {
            isRunning = false;
        }

    }

    private void displaysTimeRemaining() {
        System.out.println("Time reamining: " + time);
    }

}
