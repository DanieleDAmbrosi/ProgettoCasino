package com.casino.comm.messages;
import com.casino.comm.visitors.VisitorServer;

public abstract class Message{
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
    //public abstract void accept(com.casino.client.visitors.Visitor visitor);
}
