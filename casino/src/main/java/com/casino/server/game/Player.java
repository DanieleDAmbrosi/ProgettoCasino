package com.casino.server.game;

import java.util.ArrayList;

import com.casino.comm.player.Bet;
import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Player {
    private final ClientConnectionHandler clientConnectionHandler;
    private final PlayerState playerState;
    private final String name;
    private ArrayList<Bet> bets = new ArrayList<>();

    public Player(ClientConnectionHandler clientConnectionHandler, PlayerState playerState, String name) {
        this.clientConnectionHandler = clientConnectionHandler;
        this.playerState = playerState;
        this.name = name;
    }

    @Override
    public String toString(){
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

    public void setBets(ArrayList<Bet> bets){
        this.bets = bets;
    }

    public ArrayList<Bet> getBets(){
        return bets;
    }
}
