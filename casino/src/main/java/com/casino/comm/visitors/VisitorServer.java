package com.casino.comm.visitors;

import com.casino.comm.messages.Message;
import com.casino.comm.messages.ResetConnectionMessage;
import com.casino.server.game.Game;

public class VisitorServer {
    private final Game game;
    private final int id;

    public VisitorServer(Game game, int id) {
        this.game = game;
        this.id = id;
    }

    public void visit(Message message){
        
    }

    public void visit(ResetConnectionMessage resetConnectionMessage){
        game.removePlayer(id, resetConnectionMessage);
    }
}
