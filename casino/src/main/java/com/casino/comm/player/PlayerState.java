package com.casino.comm.player;

import java.util.ArrayList;

public class PlayerState {
    public float cash = 0.0f;
    public ArrayList<Bet> bets = new ArrayList<>();
    public boolean playing = false;

    public boolean addBet(Bet bet) {
        if (cash - bet.getMoney() < 0)
            return false;
        bets.add(bet);
        return true;
    }
}
