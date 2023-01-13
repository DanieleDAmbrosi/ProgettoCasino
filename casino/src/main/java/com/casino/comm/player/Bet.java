package com.casino.comm.player;

public class Bet {
    private float money = 0.0f;

    private Box box = null;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Bet() {
    }
}
