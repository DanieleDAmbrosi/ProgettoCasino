package com.casino.comm.player;

import java.io.Serializable;

public class Box implements Serializable{
    private int number;    
    private boolean color; //0 = black, 1 = red
    public Box(int number) {
        this.number = number;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.valueOf(number);
    }
}

