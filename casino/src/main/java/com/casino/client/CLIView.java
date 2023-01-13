package com.casino.client;

import java.io.IOException;
import java.util.Scanner;

import com.casino.comm.messages.*;
import com.casino.comm.player.Bet;
import com.casino.comm.player.Box;

public class CLIView implements View {

    private SendMessageToServer sendMessageToServer;

    /**
     * Empty constructor
     */
    public CLIView() {
        Thread thread = new Thread(() -> {
            clearScreen();
            inputJoinGame();
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void setSendMessageToServer(SendMessageToServer sendMessageToServer) {
        this.sendMessageToServer = sendMessageToServer;
    }

    @Override
    public void resetConnection() {
        clearScreen();
        System.out.println("DISCONNECTED");
    }    

    @Override
    public void close() {
        System.out.println("Server is not responding");
    }

    /**
     * This method is called to clear the console
     */
    private void clearScreen() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String[] commandStrings = { "clear" };
                Runtime.getRuntime().exec(commandStrings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBoard() {
        System.out.println("ecco la board");
    }

    public void doABet() {
        Thread thread = new Thread(() -> {
            clearScreen();
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("Do you want to bet?");
                System.out.println("[ 1 ] -> " + "YES");
                System.out.println("[ 0 ] -> " + "NO");
                if (inputYesOrNo(input) == 0) {
                    System.out.println("Ok");
                    break;
                }
                printBoard();
                System.out.println("Do a bet:");
                Bet bet = inputABet(input);
                DoABetMessage doABetMessage = new DoABetMessage();
                //doABetMessage.bet = bet;
                sendMessageToServer.sendBet(doABetMessage);
            }

        });
        thread.setDaemon(true);
        thread.start();

    }

    private void inputJoinGame(){
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Press [ 1 ] to play?");            
            if (inputNumber(input) == 1) {
                System.out.println("The game is starting...");
                break;
            }            
        }
        sendMessageToServer.joinGame();
    }

    private int inputYesOrNo(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        intInputValue = inputNumber(input);
        while (intInputValue != 1 || intInputValue != 0) {
            System.out.println("Repeat, the input is wrong");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }

    private Bet inputABet(Scanner input) {

        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("What box?");
        int intInputBox;
        intInputBox = inputNumber(input);
        while (intInputBox < 0 || intInputBox > 50) {
            System.out.println("Repeat, the input is wrong");
            intInputBox = inputNumber(input);
        }
        Box box = new Box(intInputBox);

        System.out.println("How much money?");
        int intInputCash;
        intInputCash = inputNumber(input);
        while (intInputCash < 10 || intInputCash > 1000) {
            System.out.println("Repeat, the input is wrong");
            intInputCash = inputNumber(input);
        }
        Bet bet = new Bet();
        bet.setBox(box);
        bet.setMoney(intInputCash);

        return bet;
    }

    private int inputNumber(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue = 0;
        while (true) {
            String inputString = input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                return intInputValue;
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, repeat");
            }
        }
    }

}
