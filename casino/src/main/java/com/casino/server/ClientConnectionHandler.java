package com.casino.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.casino.comm.messages.*;
import com.casino.comm.visitors.VisitorClient;
import com.casino.comm.visitors.VisitorServer;

public class ClientConnectionHandler extends Thread{
    int id;

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;

    private boolean running = true;

    public ClientConnectionHandler(int id, ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket socket) {
        this.id = id;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
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
            Message message = null;

            try {
                message = (Message) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                running = false;
            }

            if(message != null) message.accept(new VisitorServer(outputStream));
        }
        forceClose();
    }

    private void sendUpdate(Object object) {
    }

    public void forceClose() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
