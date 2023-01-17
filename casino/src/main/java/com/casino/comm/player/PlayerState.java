package com.casino.comm.player;

import java.io.Serializable;


public class PlayerState implements Serializable {
    public PlayerState(){
        
    }
    public PlayerState(int cash) {
        this.cash=cash;
    }
    public float cash = 0.0f;
    public boolean playing = false;
}
