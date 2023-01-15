package com.casino.client;

import com.casino.comm.messages.*;
import com.casino.comm.player.PlayerState;
import com.casino.server.game.Player;

/**
 * Interface that defines the methods used by both the CLI view and the GUI view
 */
public interface View {

    /**
     * Setter method for the SendMessageToServer object
     *
     * @param sendMessageToServer is the object to set
     */
    void setSendMessageToServer(SendMessageToServer sendMessageToServer);

    /**
     * Force close connection
     * 
     */
    void resetConnection();

    /**
     * Ask to user to do a bet
     * 
     */
    void doABet(PlayerState playerState, int seconds);

    /**
     * Close bets
     * 
     */
    void closeBets();

    /**
     * Close the connection
     * 
     */
    void close();

    
    
}
