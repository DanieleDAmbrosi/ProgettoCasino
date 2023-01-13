package com.casino.comm.visitors;

import java.io.IOException;

import com.casino.comm.messages.DoABetMessage;
import com.casino.comm.messages.JoinGameMessage;
import com.casino.comm.messages.ResetConnectionMessage;
import com.casino.server.ClientConnectionHandler;
import com.casino.server.game.Game;

public class VisitorServer {
    private final Game game;
    private final String id;
    private final ClientConnectionHandler clientConnectionHandler;

    public VisitorServer(Game game, String id, ClientConnectionHandler clientConnectionHandler) {
        this.game = game;
        this.id = id;
        this.clientConnectionHandler = clientConnectionHandler;
    }

    public void visit(ResetConnectionMessage resetConnectionMessage){
        game.removePlayer(id, resetConnectionMessage);
    }

    public void visit(JoinGameMessage joinGameMessage){
        game.addPlayer(clientConnectionHandler, joinGameMessage.name, id);
        try {
            clientConnectionHandler.sendMessage(new DoABetMessage());
            System.out.println("Sent a message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
