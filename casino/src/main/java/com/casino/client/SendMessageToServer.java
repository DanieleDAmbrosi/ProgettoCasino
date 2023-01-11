package com.casino.client;
import com.casino.comm.messages.*;;

public class SendMessageToServer {
    private final ConnectionHandlerClientSide connectionHandlerClientSide;

    public SendMessageToServer (ConnectionHandlerClientSide connectionHandlerClientSide){
        this.connectionHandlerClientSide = connectionHandlerClientSide;
    }

    public void sendMessage(Message message) {
        connectionHandlerClientSide.sendMessage(message);
    }
    public void sendAskWantToPlay(AskWantToPlayMessage askWantToPlayEvent) {
        connectionHandlerClientSide.sendMessage(askWantToPlayEvent);
    }
    
}
