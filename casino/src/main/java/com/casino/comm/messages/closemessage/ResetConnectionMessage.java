package com.casino.comm.messages.closemessage;

import com.casino.comm.messages.Message;
import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;

public class ResetConnectionMessage extends Message{

    public String message = "";

    @Override
    public void accept(VisitorServer visitor) {
        visitor.visit(this);
        
    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);
    }
}
