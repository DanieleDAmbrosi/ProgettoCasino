package com.casino.server.game;

import java.util.ArrayList;

import com.casino.comm.player.Bet;
import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Player {
    private final ClientConnectionHandler clientConnectionHandler;
    private PlayerState playerState;
    private final String name;
    private ArrayList<Bet> bets = new ArrayList<>();

    public Player(ClientConnectionHandler clientConnectionHandler, PlayerState playerState, String name) {
        this.clientConnectionHandler = clientConnectionHandler;
        this.playerState = playerState;
        playerState.cash = 1000;
        this.name = name;
    }

    public Player(ClientConnectionHandler clientConnectionHandler, String name) {
        this.clientConnectionHandler = clientConnectionHandler;
        playerState=new PlayerState(1000);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public ClientConnectionHandler getClientConnectionHandler() {
        return clientConnectionHandler;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public String getName() {
        return name;
    }

    public void setBets(ArrayList<Bet> bets) {
        this.bets = bets;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void setPlayerStateCash(float cash) {
        playerState.cash = cash;
    }

    public void setPlaying(boolean b) {
        playerState.playing = b;
    }
}
