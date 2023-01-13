package com.casino.comm.visitors;

import com.casino.comm.messages.JoinGameMessage;
import com.casino.comm.messages.ResetConnectionMessage;
import com.casino.server.ClientConnectionHandler;
import com.casino.server.game.Game;

public class VisitorServer {
    private final Game game;
    private final int id;
    private final ClientConnectionHandler clientConnectionHandler;

    public VisitorServer(Game game, int id, ClientConnectionHandler clientConnectionHandler) {
        this.game = game;
        this.id = id;
        this.clientConnectionHandler = clientConnectionHandler;
    }

    public void visit(ResetConnectionMessage resetConnectionMessage){
        game.removePlayer(id, resetConnectionMessage);
    }

    public void visit(JoinGameMessage joinGameMessage){
        game.addPlayer(clientConnectionHandler, joinGameMessage.name);
    }
}
