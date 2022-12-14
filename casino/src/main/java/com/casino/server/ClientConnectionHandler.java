package com.casino.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.casino.comm.messages.AskWantToPlayMessage;

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
        sendUpdate(new AskWantToPlayMessage(id));
        //LISTENING()
    }

    private void sendUpdate(Object object) {
    }

    public void forceClose() {
    }


}
