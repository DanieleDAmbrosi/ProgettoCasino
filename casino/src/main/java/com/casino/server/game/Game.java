package com.casino.server.game;

import java.util.HashMap;

import com.casino.comm.messages.ResetConnectionMessage;
import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Game {

    //private ArrayList<Player> players = new ArrayList<>();
    private HashMap<String, Player> players = new HashMap<>();

    public Game(){

    }

    public Boolean addPlayer(ClientConnectionHandler client, String name, String id){
        Player player = new Player(client, new PlayerState(), name);
        return players.put(id, player) != null;
    }

    public void removePlayer(String id, ResetConnectionMessage resetConnectionMessage) {
        System.out.println("Player " + players.remove(id).toString() + " left the lobby");
    }

    public Player getPlayer(String id){
        return players.get(id);
    }
}