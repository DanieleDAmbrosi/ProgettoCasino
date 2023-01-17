package com.casino.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.casino.comm.messages.DoABetMessage;
import com.casino.comm.messages.Message;
import com.casino.comm.messages.SendRouletteResultMessage;
import com.casino.comm.messages.closemessage.ResetConnectionMessage;
import com.casino.comm.player.Bet;
import com.casino.comm.player.Box;
import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Game extends Thread{

    //private ArrayList<Player> players = new ArrayList<>();
    private HashMap<String, Player> players = new HashMap<>();
    private boolean running;
    private final long TIME_SEC = 15;
    private final long BET_TIME = TIME_SEC * 1000;
    private final long WAIT_FOR_BET = (TIME_SEC + 20) * 1000;
    private int winningNumber = -1;
    private final Timer t = new Timer();  
    private final TimerTask waitForBet = new TimerTask() {
        @Override
        public void run() {
            System.out.println("Sending bet requests");
            sendBroadcast(new DoABetMessage());
            try {
                sleep(WAIT_FOR_BET);
                estractNumber.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private final Thread estractNumber = new Thread() {
        @Override
        public void run() {
            winningNumber = new Random().nextInt(37);
            System.out.println("Winning number: " + winningNumber);
            sendBroadcast(new SendRouletteResultMessage());
        }
    };

    public Game(){
        running = true;
    }

    public void addPlayer(ClientConnectionHandler client, String name, String id){
        PlayerState initialPlayerState = new PlayerState();
        initialPlayerState.cash = 1000;
        Player player = new Player(client, initialPlayerState, name);
        players.put(id, player);
        System.out.println("Player " + player + " joined the lobby");
        System.out.println("Name: " + player.getName());
    }

    public void removePlayer(String id, ResetConnectionMessage resetConnectionMessage) {
        Player player = players.remove(id);
        System.out.println("Player " + player + " left the lobby");
        System.out.println("Name: " + player.getName());
    }

    public Player getPlayer(String id){
        return players.get(id);
    }

    @Override
    public void run() {
        t.scheduleAtFixedRate(waitForBet, 0, WAIT_FOR_BET);
        while (running) {
            
        }
        stopGame();
    }

    private void stopGame(){
        sendBroadcast(new ResetConnectionMessage());
        players = new HashMap<>();
        System.out.println("Game ended");
    }

    private void sendBroadcast(Message message){
        for(Map.Entry<String, Player> player : players.entrySet()) {
            try {
                if(message instanceof DoABetMessage){
                    DoABetMessage doABetMessage  = (DoABetMessage) message;
                    doABetMessage.playerState = player.getValue().getPlayerState();
                    doABetMessage.EndTimer = System.currentTimeMillis() + BET_TIME;
                    player.getValue().getClientConnectionHandler().sendMessage(doABetMessage);
                }else if(message instanceof SendRouletteResultMessage){
                    SendRouletteResultMessage sendRouletteResultMessage = (SendRouletteResultMessage) message;
                    sendRouletteResultMessage.winningNumber = winningNumber;
                    sendRouletteResultMessage.winningCash = getWinningCash(player.getValue().getBets());
                    player.getValue().getClientConnectionHandler().sendMessage(sendRouletteResultMessage);
                }else player.getValue().getClientConnectionHandler().sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private float getWinningCash(ArrayList<Bet> bets) {
        float winningCash = 0.0f;
        for (Bet bet : bets) {
            if(winningNumber == 0) break;
            int betNumber = bet.getBox().getNumber();
            if(betNumber >= 0 && betNumber <= 36){
                if(betNumber == winningNumber){
                    winningCash += bet.getMoney() * 36;
                }
            }else if(betNumber == 37 && (winningNumber >= 1 && winningNumber <= 12)){
                winningCash += bet.getMoney() * 3;
            }else if(betNumber == 38 && (winningNumber >= 13 && winningNumber <= 24)){
                winningCash += bet.getMoney() * 3;
            }else if(betNumber == 39 && (winningNumber >= 25 && winningNumber <= 36)){
                winningCash += bet.getMoney() * 3;
            }else if(betNumber == 40 && (winningNumber >= 1 && winningNumber <= 18)){
                winningCash += bet.getMoney() * 2;
            }else if(betNumber == 41 && (winningNumber >= 19 && winningNumber <= 36)){
                winningCash += bet.getMoney() * 2;
            }else if(betNumber == 44 && (winningNumber % 2 == 0)){
                winningCash += bet.getMoney() * 2;
            }else if(betNumber == 45 && (winningNumber % 2 == 1)){
                winningCash += bet.getMoney() * 2;
            }else if(betNumber == 46 && (winningNumber % 3 == 0 && winningNumber != 0)){
                winningCash += bet.getMoney() * 3;
            }else if(betNumber == 47 && (winningNumber % 3 == 1)){
                winningCash += bet.getMoney() * 3;
            }else if(betNumber == 48 && (winningNumber % 3 == 2)){
                winningCash += bet.getMoney() * 3;
            }
        }
        return winningCash;
    }

    public void updatePlayer(String id, DoABetMessage doABetMessage) {
        players.get(id).setBets(doABetMessage.bets);
    }
}