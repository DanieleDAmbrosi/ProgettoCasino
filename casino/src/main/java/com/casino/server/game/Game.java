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
    private final long BET_TIME = 60 * 1000;
    private final long WAIT_FOR_BET = 80 * 1000;

    public Game(){
        running = true;
    }

    public void addPlayer(ClientConnectionHandler client, String name, String id){
        Player player = new Player(client, new PlayerState(), name);
        System.out.println("Player " + player + " joined the lobby");
    }

    public void removePlayer(String id, ResetConnectionMessage resetConnectionMessage) {
        System.out.println("Player " + players.remove(id).toString() + " left the lobby");
    }

    public Player getPlayer(String id){
        return players.get(id);
    }

    @Override
    public void run() {
        Timer t = new Timer();  
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                sendBroadcast(new DoABetMessage());
            }
        };
        t.scheduleAtFixedRate(tt, 0, WAIT_FOR_BET);
        while (running) {
            
        }
        stopGame();
    }

    private void stopGame(){
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
}