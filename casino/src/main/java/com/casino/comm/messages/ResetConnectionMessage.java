package com.casino.comm.messages;

import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;

public class ResetConnectionMessage extends Message{

    public String message = "";

    @Override
    public void accept(VisitorServer visitor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void accept(VisitorClient visitor) {
        // TODO Auto-generated method stub

    }
    
}
