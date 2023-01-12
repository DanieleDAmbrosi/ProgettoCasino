package com.casino.comm.messages;
import java.io.Serializable;

import com.casino.comm.visitors.*;

public abstract class Message implements Serializable{
    private int clientIndex;
    private boolean gameRunning = true;

    public int getClientIndex() {
        return clientIndex;
    }
    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }
    public boolean isGameRunning() {
        return gameRunning;
    }
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
    public abstract void accept(VisitorServer visitor);
    public abstract void accept(VisitorClient visitor);
}
