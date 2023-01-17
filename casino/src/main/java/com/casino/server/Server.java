package com.casino.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.casino.server.game.Game;


/**
 * This class manages the Server
 */
public class Server{
    /**
     * This attribute is the port number where the server is listening
     * 
     */
    private static int PORT;
    private static final ArrayList<ClientConnectionHandler> ClientList = new ArrayList<>();
    private static final int MAX_LOBBY_SIZE = 10;

    private final Object LOCKClientList = new Object();
    /**
     * This is the constructor of the Server class
     * 
     * @param PORT port number
     */
    public Server(int PORT){
        Server.PORT = PORT;
    }
    /**
     * This method runs the server, accepting all the connections pending
     * 
     * @param serverSocket the socket where clients connect
     */
    public void run(ServerSocket serverSocket) {
        try {
            Game game = new Game();
            game.start();
            while(true){
                Socket clientSocket;
                
                clientSocket = serverSocket.accept();
                System.out.println("Client " + clientSocket + " is connected");

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                ClientConnectionHandler client = new ClientConnectionHandler(out, in, clientSocket, game);

                ClientList.add(client);

                client.start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(serverSocket != null){
                    stopAllClients();
                    serverSocket.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * Force all Clients to close 
     */
    public void stopAllClients(){
        synchronized(LOCKClientList){
            for(ClientConnectionHandler client : ClientList)
                client.forceClose();
            ClientList.clear();
        }
    }
    /**
     * Setups the server and launches it
     * 
     * @param args argument: [-IP] address [-PORT] port [-CLI/GUI] view
     */
    public static void main(String[] args){
        //Server setup from CLI
        final int N_ARGS = 6; //the number of the args needed

        HashMap<String, String> hashMap = new HashMap<>();

        int i = 0;
        String arg = "";

        while(i < args.length && args[i].startsWith("-")){
            arg = args[i++];

            if(i < args.length) hashMap.put(args[i++], arg.substring(1));
            else System.err.println("-" + arg + " requires an argument");
        }

        if(N_ARGS != args.length){
            System.err.println("Usage: ParseCmdLine [-IP] address [-PORT] port [-CLI/GUI] view");
        }

        parseServer(hashMap);

        Server server = new Server(PORT);

        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.err.println("Connection Failed");
        }

        server.run(serverSocket);
    }

    /**
     * parsing the PORT chosen by user
     * 
     * @param hashMap used to parse args
     */
    private static void parseServer(HashMap<String, String> hashMap) {
        String port = hashMap.get("port");

        if(port != null){
            int iPort = 0;
            try {
                iPort = Integer.parseInt(port);
            } catch (NumberFormatException e) {
                System.err.println("Usage: [-PORT] port --- [-PORT] has to be an int");
            }
            PORT = iPort;
            System.out.println("The port has been chosen correctly!!!");
        }
        else System.err.println("Usage: [-PORT] port --- [-PORT] param missing");
    }
}
