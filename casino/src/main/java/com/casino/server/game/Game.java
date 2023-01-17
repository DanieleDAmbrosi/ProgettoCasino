package com.casino.server.game;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.casino.comm.messages.DoABetMessage;
import com.casino.comm.messages.Message;
import com.casino.comm.messages.closemessage.ResetConnectionMessage;
import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Game extends Thread{

    //private ArrayList<Player> players = new ArrayList<>();
    private HashMap<String, Player> players = new HashMap<>();
    private boolean running;
    private final long TIME_SEC = 17;
    private final long BET_TIME = TIME_SEC * 1000;
    private final long WAIT_FOR_BET = (TIME_SEC + 20) * 1000;
    private final Timer t = new Timer();  
    private final TimerTask tt = new TimerTask() {
        @Override
        public void run() {
            System.out.println("Sending bet requests");
            sendBroadcast(new DoABetMessage());
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
        t.scheduleAtFixedRate(tt, 0, WAIT_FOR_BET);
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
                }else player.getValue().getClientConnectionHandler().sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePlayer(String id, DoABetMessage doABetMessage) {
        PlayerState temp = doABetMessage.playerState;
        PlayerState playerState = players.get(id).getPlayerState();
    }
}