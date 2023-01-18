package com.casino.comm.messages;

import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;

public class SendRouletteResultMessage extends Message{

    public int winningNumber;
    public float winningCash;

    @Override
    public void accept(VisitorServer visitor) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);
    }
    
}
