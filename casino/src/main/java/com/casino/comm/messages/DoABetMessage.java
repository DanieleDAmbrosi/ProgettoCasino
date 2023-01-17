package com.casino.comm.messages;

import com.casino.comm.visitors.*;

import java.util.ArrayList;

import com.casino.comm.player.*;

public class DoABetMessage extends Message {

    public PlayerState playerState = new PlayerState();
    public long EndTimer = 0;
    public ArrayList<Bet> bets = new ArrayList<>();

    public boolean addBet(Bet bet) {
        if (bet == null)
            return false;
        if (playerState.cash - bet.getMoney() < 0)
            return false;
        playerState.cash -= bet.getMoney();
        bets.add(bet);
        return true;
    }

    @Override
    public void accept(VisitorServer visitor) {
        visitor.visit(this);

    }

    @Override
    public void accept(VisitorClient visitor) {
        visitor.visit(this);
    }

}
