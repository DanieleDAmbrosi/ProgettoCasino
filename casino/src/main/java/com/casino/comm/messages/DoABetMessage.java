package com.casino.comm.messages;

import com.casino.comm.visitors.*;
import com.casino.comm.player.Bet;

public class DoABetMessage extends Message {

    public Bet bet;

    @Override
    public void accept(VisitorServer visitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);

    }

}
