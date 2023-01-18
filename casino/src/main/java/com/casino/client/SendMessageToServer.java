package com.casino.client;
import java.util.ArrayList;

import com.casino.comm.messages.*;
import com.casino.comm.messages.closemessage.ResetConnectionMessage;
import com.casino.comm.player.Bet;

public class SendMessageToServer {
    private final ConnectionHandlerClientSide connectionHandlerClientSide;

    public SendMessageToServer (ConnectionHandlerClientSide connectionHandlerClientSide){
        this.connectionHandlerClientSide = connectionHandlerClientSide;
    }

    public void sendMessage(Message message) {
        connectionHandlerClientSide.sendMessage(message);
    }
    
    public void sendBet(DoABetMessage doABetMessage){        
        //doABetMessage.playerState.playing = true;
        connectionHandlerClientSide.sendMessage(doABetMessage);
    }
    public void joinGame(String username){
        JoinGameMessage joinGameMessage = new JoinGameMessage();
        joinGameMessage.name = username;
        connectionHandlerClientSide.sendMessage(joinGameMessage);
    }

    public void sendResetConnection(){
        connectionHandlerClientSide.sendMessage(new ResetConnectionMessage());
    }
    
    
}
