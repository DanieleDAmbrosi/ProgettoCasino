package com.casino.comm.visitors;

import java.io.IOException;

import com.casino.comm.messages.*;
import com.casino.comm.player.Bet;
import com.casino.comm.player.PlayerState;
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

    public void visit(ResetConnectionMessage resetConnectionMessage) {
        game.removePlayer(id, resetConnectionMessage);
    }

    public void visit(JoinGameMessage joinGameMessage) {
        game.addPlayer(clientConnectionHandler, joinGameMessage.name, id);
        try {
            DoABetMessage doABetMessage = new DoABetMessage();
            doABetMessage.playerState = new PlayerState();
            doABetMessage.playerState.cash = 1000;
            doABetMessage.EndTimer = System.currentTimeMillis() + 10000;
            clientConnectionHandler.sendMessage(doABetMessage);
            System.out.println("Sent a message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void visit(DoABetMessage doABetMessage){
        for(Bet bet : doABetMessage.playerState.bets){
            System.out.println("bet: " + bet.getMoney() + "$ on " + bet.getBox().toString());
        }
        
    }
    public void visit(SendRouletteResultMessage sendRouletteResultMessage){

    }

    public void visit(CloseConnectionMessage closeConnectionMessage) {
        game.removePlayer(id, closeConnectionMessage);
    }

}
