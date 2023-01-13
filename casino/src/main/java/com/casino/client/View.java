package com.casino.client;

import com.casino.comm.messages.*;

/**
 * Interface that defines the methods used by both the CLI view and the GUI view
 */
public interface View {

    // public void askWantToPlay(AskWantToPlayMessage askWantToPlay);

    /**
     * Setter method for the SendMessageToServer object
     *
     * @param sendMessageToServer is the object to set
     */
    void setSendMessageToServer(SendMessageToServer sendMessageToServer);

    void resetConnection();

    /**
     * 
     * @param sendMessageToServer
     */
    void doABet();

    /**
     * Method called from the VisitorClient when the clientHandler received an
     * YouCanPlay message
     */
    void youCanPlay();

    /**
     * Method called from the VisitorClient when the clientHandler received an
     * YouHaveToWait message
     */
    void youHaveToWait();

    /**
     * Method called from the VisitorClient every time the clientHandler received an
     * updateBoard message
     *
     * @param usersArray      is the ArrayList of users taking part to the game
     * @param board           is the object Board describe the game field
     * @param isShowReachable is a boolean that indicates if the printed board has
     *                        to show the reachable boxes
     * @param currentPlaying  is the integer index of the gamer playing in this turn
     * @param indexClient     is the index of the client associated with the player
     * @param someoneDead     true if one of the three players died
     */
    // void updateBoard(ArrayList<User> usersArray, Board board, boolean
    // isShowReachable, int currentPlaying, int indexClient, boolean someoneDead);

    /**
     * Method called from the VisitorClient of the first player connected when the
     * ClientHandler receives an AskNPlayer message.
     */
    void askNPlayer();

    /**
     * 
     * @param user is the username that client want to use
     */
    void setUsername(String user);

    /**
     * Method called from the VisitorClient when the ClientHandler receives an
     * AskBuildEVent message and firstTime is false.
     *
     * @param rowWorker      is the row of the box occupied by the worker
     * @param columnWorker   is the column of the box occupied by the worker
     * @param indexWorker    is the integer index of the worker the player wants to
     *                       move
     * @param isWrongBox     is a boolean that indicates if the move is wrong
     * @param isFirstTime    is a boolean that indicates if this is the first move
     *                       tried in this turn
     * @param isSpecialTurn  is a boolean used to identify special moves
     * @param clientIndex    is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done           is a boolean used to indicates if the move turn is over
     */
    void anotherBuild(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime,
            boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done);

    /**
     * This method is used when is not the player's turn
     *
     * @param clientIndex index client
     */
    void isNotMyTurn(int clientIndex);

    /**
     * Method used to send, using the SendMessageToServer object, a Pong message to
     * the server after received a ping
     *
     * @param objHeartBeat is the message Ping received from the visitorClient
     */
    void printHeartBeat(Message objHeartBeat);

    /**
     * Method used to send to the server the request of closing the connection
     *
     * @param indexClient      is the index associated to the client
     * @param GameNotAvailable is a boolean used to indicate if the connection will
     *                         be close because the game is already
     *                         started or because of a problem
     */
    void closingConnectionEvent(int indexClient, boolean GameNotAvailable);

    /**
     * Method used to close client when server is not responding
     */
    void close();
}
