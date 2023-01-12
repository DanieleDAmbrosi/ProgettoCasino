package com.casino.server.game;

import java.util.ArrayList;

import com.casino.comm.player.PlayerState;
import com.casino.server.ClientConnectionHandler;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();

    public Game(){

    }

    public Boolean addPlayer(ClientConnectionHandler client){
        return players.add(new Player(client, new PlayerState()));
    }


}