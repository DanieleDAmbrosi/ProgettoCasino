package com.casino.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;


public class Client {

    private static int SERVER_PORT;
    private static InetAddress SERVER_ADDRESS;
    private static View viewClient;

    public Client(InetAddress serverAddress, int serverPort) {
        Client.SERVER_ADDRESS = serverAddress;
        Client.SERVER_PORT = serverPort;
    }    
    public static void main(String[] args) {
        final int N_ARGS = 6; // the number of the args needed

        HashMap<String, String> hashMap = new HashMap<>();

        int i = 0;
        String arg = "";

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            if (i < args.length)
                hashMap.put(args[i++], arg.substring(1));
            else {
                System.err.println("-" + arg + " requires an argument");
                return;
            }
        }

        if (N_ARGS != args.length) {
            System.err.println("Usage: ParseCmdLine [-SERVERIP] address [-SERVERPORT] port [-CLI/GUI] view");
            return;
        }

        parseClient(hashMap);

        Socket clientSocket = null;

        try {
            clientSocket = new Socket(Client.SERVER_ADDRESS, Client.SERVER_PORT);

            System.out.println("Connected to server: [" + SERVER_ADDRESS.getHostAddress() + "] address ["
                    + SERVER_PORT + "] port");
        } catch (IOException e) {
            System.err.println("Connection Failed");
            return;
        }

        if (clientSocket != null) {
            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConnectionHandlerClientSide connectionHandlerClientSide = new ConnectionHandlerClientSide(inputStream,
                    outputStream, clientSocket, viewClient);
            connectionHandlerClientSide.listening();
        }

    }

    /**
     * parsing the PORT chosen by user
     * 
     * @param hashMap used to parse args
     * @throws Exception
     */
    private static void parseClient(HashMap<String, String> hashMap) {
        boolean redo = false;

        String ipAddress = hashMap.get("ip");
        if (ipAddress != null) {
            try {
                SERVER_ADDRESS = InetAddress.getByName(ipAddress);
            } catch (Exception e) {
                redo = true;
            }
        } else {
            redo = true;
        }

        String port = hashMap.get("port");
        if (port != null) {
            int socketPort = Integer.parseInt(port);
            SERVER_PORT = socketPort;
        } else {
            redo = true;
        }
        String view = hashMap.get("view").toUpperCase();
        if (view != null) {
            switch (view) {
                case "GUI":
                    // viewClient = new ViewGUIController();
                    System.err.println("gui not implemented");
                    redo = true;
                    break;
                case "CLI":
                    viewClient = new CLIView();
                    break;
                default:
                    redo = true;
            }
        } else {
            redo = true;
        }

        if (redo) {
            System.err.println("Connection failed!");
            System.err.println("Usage: ParseCmdLine [-SERVER_IP] address [-SERVER_PORT] port [-CLI/GUI] view");
        }
    }

}
