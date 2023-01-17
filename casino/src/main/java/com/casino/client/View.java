package com.casino.client;
import com.casino.comm.player.PlayerState;

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
    void doABet(PlayerState playerState, long endTimer);

    /**
     * Ask the user if he want to start the game
     * 
     */
    void welcome();

    void showResults(int winnerNumber, float winningCash);
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
