package com.casino.server.game;

import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Player {
    private final ClientConnectionHandler clientConnectionHandler;
    private final PlayerState playerState;
    private final String name;

    public Player(ClientConnectionHandler clientConnectionHandler, PlayerState playerState, String name) {
        this.clientConnectionHandler = clientConnectionHandler;
        this.playerState = playerState;
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
