package com.casino.comm.ack;
import com.casino.comm.messages.*;
import com.casino.comm.visitors.*;

public class AckResetConnection extends Message {

    @Override
    public void accept(VisitorServer visitor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void accept(VisitorClient visitor) {       
        throw new UnsupportedOperationException();    
    }
    
}
