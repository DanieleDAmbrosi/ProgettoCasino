package com.casino.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import com.casino.comm.messages.*;
import com.casino.comm.visitors.VisitorServer;
import com.casino.server.game.Game;

public class ClientConnectionHandler extends Thread{
    private final String id;

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;
    private final Game game;

    private boolean running = true;

    public ClientConnectionHandler(ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket socket, Game game) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
        this.game = game;
        id = UUID.randomUUID().toString();
    }

    @Override
    public void run(){
        listening();
    }

    /**
     * Receives all the messages from the client
     */
    private void listening() {
        while(running){
            Message genericMessage = null;

            try {
                genericMessage = (Message) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                running = false;
            }
            if(genericMessage instanceof ResetConnectionMessage) {
                close((ResetConnectionMessage) genericMessage);
                running = false;
            }
            if(genericMessage != null) genericMessage.accept(new VisitorServer(game, id, this));
        }
        forceClose();
    }

    private void close(ResetConnectionMessage resetConnectionMessage) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client " + socket + " has disconnected");
        if(resetConnectionMessage.message != "") System.out.println("Message: " + resetConnectionMessage.message);
    }

    public void sendMessage(Message message) throws IOException {
        outputStream.writeObject(message);
    }

    public void forceClose() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client " + socket + " has been disconnected forcefully");
        ResetConnectionMessage resetConnectionMessage = new ResetConnectionMessage();
        resetConnectionMessage.message = "You have been disconnected forcefully";
        try {
            sendMessage(resetConnectionMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
