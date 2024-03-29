package com.casino.client;

import com.casino.comm.messages.*;
import com.casino.comm.messages.closemessage.ResetConnectionMessage;

import java.io.*;
import java.net.Socket;
import com.casino.comm.visitors.VisitorClient;

public class ConnectionHandlerClientSide {
    /**
     * Stream Object used to receive Message
     */
    private final ObjectInputStream inputStream;
    /**
     * Stream Object used to send Message
     */
    private final ObjectOutputStream outputStream;
    /**
     * Socket Object used to create a connection with the server
     */
    private final Socket socket;
    /**
     * View Object that contains the view instance the user has choose
     */
    private final View view;
    /**
     * Object use to guarantee the mutual exclusion to access a piece of code
     */
    private final Object LOCK = new Object();
    /**
     * Boolean attribute used to control if the connection is close
     */
    private boolean closed = false;

    /**
     * Constructor with parameters that characterize a client instance
     *
     * @param inputStream  input stream of the socket
     * @param outputStream output stream of the socket
     * @param socket       client socket
     * @param view         view client's side
     */
    public ConnectionHandlerClientSide(ObjectInputStream inputStream, ObjectOutputStream outputStream, Socket socket,
            View view) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
        this.view = view;
    }

    /**
     * The first time the method is launched it links a SendMessageToServer object
     * to the view.
     * Then there's a loop ending only when it received a
     * CloseConnectionFromServerEvent object and this is used for listening
     * to the input stream and for reading objects once received. If the message
     * received isn't null
     * it will be accepted and read from the VisitorClient
     */
    public void listening() {
        SendMessageToServer sendMessageToServer = new SendMessageToServer(this);
        view.setSendMessageToServer(sendMessageToServer);
        view.welcome();
        try {
            while (!closed) {
                Message message = null;
                try {
                    message = (Message) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    closed = true;
                    view.close();
                }

                if (message instanceof ResetConnectionMessage){
                    closed = true;
                    view.close();
                }

                if (message != null)
                    message.accept(new VisitorClient(view));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    /**
     * This method is used to send message to the server using the output stream
     * object
     * until the closed condition is true. Every time it sends a message it clears
     * the output stream
     *
     * @param message is the object we want to send
     */
    public void sendMessage(Message message) {
        if (!closed) {
            synchronized (LOCK) {
                try {
                    outputStream.writeObject(message);
                    outputStream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is used to close the client connection with the server
     * established with the socket object
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
