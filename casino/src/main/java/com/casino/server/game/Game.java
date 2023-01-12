package com.casino.server.game;

import java.util.ArrayList;

import com.casino.comm.messages.ResetConnectionMessage;
import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();

    public Game(){

    }

    public Boolean addPlayer(ClientConnectionHandler client, String name){
        return players.add(new Player(client, new PlayerState(), name));
    }

    public void removePlayer(int id, ResetConnectionMessage resetConnectionMessage) {
        Player player = null;
        if((player = getPlayer(id)) != null) players.remove(player);
        System.out.println("Player " + player.toString() + " left the lobby");
    }

    private Player getPlayer(int id){
        return players.get(id);
    }
}