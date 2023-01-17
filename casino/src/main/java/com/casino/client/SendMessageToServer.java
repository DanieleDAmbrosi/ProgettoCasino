package com.casino.client;
import java.util.ArrayList;

import com.casino.comm.messages.*;
import com.casino.comm.player.Bet;
import com.casino.comm.player.PlayerState;;

public class SendMessageToServer {
    private final ConnectionHandlerClientSide connectionHandlerClientSide;

    public SendMessageToServer (ConnectionHandlerClientSide connectionHandlerClientSide){
        this.connectionHandlerClientSide = connectionHandlerClientSide;
    }

    public void sendMessage(Message message) {
        connectionHandlerClientSide.sendMessage(message);
    }
    
    public void sendBet(ArrayList<Bet> bets){
        DoABetMessage doABetMessage = new DoABetMessage();
        doABetMessage.bets = bets;
        connectionHandlerClientSide.sendMessage(doABetMessage);
    }
    public void joinGame(String username){
        JoinGameMessage joinGameMessage = new JoinGameMessage();
        joinGameMessage.name = username;
        connectionHandlerClientSide.sendMessage(joinGameMessage);
    }

    public void ackDoABet(){
        
    }
    
    
}
