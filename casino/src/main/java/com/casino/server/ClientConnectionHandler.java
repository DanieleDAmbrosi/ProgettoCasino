package com.casino.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.casino.comm.messages.*;
import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;
import com.casino.server.game.Game;

public class ClientConnectionHandler extends Thread{
    int id;

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;
    private final Game game;

    private boolean running = true;

    public ClientConnectionHandler(int id, ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket socket, Game game) {
        this.id = id;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run(){
        //VUOI GIOCARE?
        sendUpdate(new Message() {
            public String text = "Test";
            public void accept(VisitorServer visitorServer){
                visitorServer.visit(this);
            }
            public void accept(VisitorClient visitorServer){
                visitorServer.visit(this);
            }
        });
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
            if(genericMessage instanceof ResetConnectionMessage) close((ResetConnectionMessage) genericMessage);
            if(genericMessage != null) genericMessage.accept(new VisitorServer(game, id));
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

    private void sendUpdate(Object object) {
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
            outputStream.writeObject(resetConnectionMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
