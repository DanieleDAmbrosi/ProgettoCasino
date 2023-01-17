package com.casino.comm.messages;

import com.casino.comm.visitors.*;
import com.casino.comm.player.*;

public class DoABetMessage extends Message {

    public PlayerState playerState = new PlayerState();
    public long EndTimer = 0;

    @Override
    public void accept(VisitorServer visitor) {
        visitor.visit(this);

    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);
    }

}
