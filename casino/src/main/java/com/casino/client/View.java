package com.casino.client;

import com.casino.comm.messages.*;

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
    void doABet();

    /**
     * Close the connection
     * 
     */
    void close();
    
}
