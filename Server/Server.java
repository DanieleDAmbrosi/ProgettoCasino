package Server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class manages the Server
 */
public class Server{
    /**
     * This attribute is the port number where the server is listening
     * 
     */
    private static int PORT;
    /**
     * This attribute is the array of Lobbies whic are playing the game
     * 
     */
    private static final ArrayList<Lobby> ListLobbies = new ArrayList<>();
    /**
     * This attribute is the array of Lobbies which are waiting to get in the game
     * 
     */
    private static final ArrayList<Lobby> WaitingListLobbies = new ArrayList<>();
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
        //TODO:
    }

    /**
     * Setups the server and launches it
     * 
     * @param args argument: [-IP] address [-PORT] port [-CLI/GUI] view
     */
    public static void main(String[] args){
        //Server setup from CLI
        final int N_ARGS = 2; //the number of the args needed

        HashMap<String, String> hashMap = new HashMap<>();

        int i = 0;
        String arg = "";

        while(i < args.length && args[i].startsWith("-")){
            arg = args[i++];

            if(i < args.length) hashMap.put(arg.substring(1), args[i++]);
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