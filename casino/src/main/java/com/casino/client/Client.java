package com.casino.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import com.casino.comm.messages.*;
import com.casino.comm.visitors.*;

public class Client extends Thread {

    private static int SERVER_PORT;
    private static InetAddress SERVER_ADDRESS;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Socket socket;

    private boolean running = true;

    public Client(InetAddress serverAddress, int serverPort) {
        Client.SERVER_ADDRESS = serverAddress;
        Client.SERVER_PORT = serverPort;
    }

    /**
     * This method runs the client, manages the connection with the server
     * 
     * @param clientSocket
     */
    private void run(Socket clientSocket) {
        try {
            socket = clientSocket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (running) {

                Message message = null;

                try {
                    message = (Message) in.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    running = false;
                }

                if (message != null){
                    
                }
                    message.accept(new VisitorClient(out));
            }
            forceClose();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {                    
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Force close the connection with server
     * 
     */
    public void forceClose() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setups the client and launches it
     * 
     * @param args argument: [-SERVERIP] address [-SERVERPORT] port [-CLI/GUI] view
     */
    public static void main(String[] args) {
        final int N_ARGS = 6; // the number of the args needed

        HashMap<String, String> hashMap = new HashMap<>();

        int i = 0;
        String arg = "";

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            if (i < args.length)
                hashMap.put(args[i++], arg.substring(1));
            else
                System.err.println("-" + arg + " requires an argument");
        }

        if (N_ARGS != args.length) {
            System.err.println("Usage: ParseCmdLine [-SERVERIP] address [-SERVERPORT] port [-CLI/GUI] view");
        }

        parseClient(hashMap);

        Client client = new Client(SERVER_ADDRESS, SERVER_PORT);

        Socket clientSocket = null;

        try {
            clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            System.out.println("Connected to the server: [" + SERVER_ADDRESS.getHostAddress() + "] address ["
                    + SERVER_PORT + "] port");
        } catch (Exception e) {
            System.err.println("Connection Failed");
        }

        client.run(clientSocket);
    }

    /**
     * parsing the PORT chosen by user
     * 
     * @param hashMap used to parse args
     */
    private static void parseClient(HashMap<String, String> hashMap) {
        String port = hashMap.get("port");
        String address = hashMap.get("address");

        if (port != null) {
            int iPort = 0;
            if (address != null) {
                InetAddress iAddress = null;
                try {
                    iPort = Integer.parseInt(port);
                    iAddress = InetAddress.getByName(address);
                } catch (NumberFormatException e) {
                    System.err.println("Usage: [-SERVERPORT] port --- [-SERVERPORT] has to be an int");
                } catch (UnknownHostException e) {
                    System.err.println("Usage: [-SERVERIP] address --- [-SERVERIP] has to be an ip like {172.10.0.1}");
                }
                SERVER_PORT = iPort;
                System.out.println("The port has been chosen correctly!!!");
            } else
                System.err.println("Usage: [-SERVERIP] address --- [-SERVERIP] param missing");
        } else
            System.err.println("Usage: [-SERVERPORT] port --- [-SERVERPORT] param missing");
    }

}
