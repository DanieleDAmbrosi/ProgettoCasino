package com.casino.server.game;

import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Player {
    private final ClientConnectionHandler clientConnectionHandler;
    private final PlayerState playerState;

    public Player(ClientConnectionHandler clientConnectionHandler, PlayerState playerState) {
        this.clientConnectionHandler = clientConnectionHandler;
        this.playerState = playerState;
    }


}
