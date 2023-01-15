package com.casino.comm.messages;

import com.casino.comm.visitors.*;
import com.casino.comm.player.*;

public class DoABetMessage extends Message {

    public PlayerState playerState;
    public int timer;

    @Override
    public void accept(VisitorServer visitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);
    }

}
