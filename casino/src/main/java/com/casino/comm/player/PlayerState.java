package com.casino.comm.player;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerState implements Serializable {
    public float cash = 0.0f;
    public ArrayList<Bet> bets = new ArrayList<>();
    public boolean playing = false;

    public boolean addBet(Bet bet) {
        if (cash - bet.getMoney() < 0)
            return false;
        cash -= bet.getMoney();
        bets.add(bet);
        return true;
    }
}
