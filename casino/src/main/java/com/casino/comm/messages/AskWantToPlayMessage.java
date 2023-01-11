package com.casino.comm.messages;

import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;

public class AskWantToPlayMessage extends Message {

    @Override
    public void accept(VisitorServer visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);
    }
}
