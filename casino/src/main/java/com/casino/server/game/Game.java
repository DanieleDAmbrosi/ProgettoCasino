package com.casino.server.game;

import java.util.HashMap;

import com.casino.comm.messages.CloseConnectionMessage;
import com.casino.comm.messages.ResetConnectionMessage;
import com.casino.comm.player.PlayerState;
import com.casino.comm.visitors.VisitorServer;
import com.casino.server.ClientConnectionHandler;

public class Game extends Thread{

    //private ArrayList<Player> players = new ArrayList<>();
    private HashMap<String, Player> players = new HashMap<>();
    private boolean running;

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
    public void removePlayer(String id, CloseConnectionMessage closeConnectionMessage) {
        System.out.println("Player " + players.remove(id).toString() + " left the lobby");
    }
    public Player getPlayer(String id){
        return players.get(id);
    }

    @Override
    public void run() {
        while (running) {
            
        }
        stopGame();
    }

    private void stopGame(){
        System.out.println("Game ended");
    }


}