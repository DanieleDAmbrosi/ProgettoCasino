package com.casino.comm.messages;

import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;

public class AckDoABet extends Message{

    @Override
    public void accept(VisitorServer visitor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void accept(VisitorClient visitor) {
        throw new UnsupportedOperationException();        
    }
    
}
