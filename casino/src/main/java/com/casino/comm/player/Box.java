package com.casino.comm.player;

import java.io.Serializable;

public class Box implements Serializable{
    private int number;    
    private boolean color; //0 = black, 1 = red
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public Box(int number) {
        this.number = number;
    }
    
    @Override
    public String toString() {
        return String.valueOf(number);
    }
}

