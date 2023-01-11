package com.casino.client;

import java.io.IOException;

import com.casino.comm.messages.AskWantToPlayMessage;
import com.casino.comm.messages.Message;

public class CLIView implements View{

    private SendMessageToServer sendMessageToServer;;

    /**
     * Empty constructor
     */
    public CLIView() {
    }

    @Override
    public void setSendMessageToServer(SendMessageToServer sendMessageToServer) {
        this.sendMessageToServer = sendMessageToServer;        
    }

    @Override
    public void youCanPlay() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void youHaveToWait() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void askNPlayer() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setUsername(String user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void anotherBuild(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime,
            boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void isNotMyTurn(int clientIndex) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void printHeartBeat(Message objHeartBeat) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closingConnectionEvent(int indexClient, boolean GameNotAvailable) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void askWantToPlay(AskWantToPlayMessage askWantToPlay) {
        Thread thread = new Thread(() -> {
            clearScreen();
            System.out.println("I'm trying to join a game");
            sendMessageToServer.sendMessage(askWantToPlay);
        });
        thread.setDaemon(true);
        thread.start();        
    }

     /**
     * This method is called to clear the console
     */
    private void clearScreen() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
