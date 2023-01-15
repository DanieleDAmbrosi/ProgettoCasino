package com.casino.comm.utility;

import com.casino.client.ConnectionHandlerClientSide;
import com.casino.comm.visitors.VisitorClient;

public class TimeChanged {
    void onChangedTime(int time) {
        if (time > 0)
            System.out.println("Time remaining " + time + " seconds");
        else {
            System.out.println("Time's up");            
        }
    }
}
