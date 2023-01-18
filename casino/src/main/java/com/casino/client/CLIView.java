package com.casino.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;

import com.casino.comm.messages.*;
import com.casino.comm.player.Bet;
import com.casino.comm.player.Box;

public class CLIView implements View {

    private SendMessageToServer sendMessageToServer;
    private Scanner input = new Scanner(System.in);
    private boolean canInput = true;

    /**
     * Empty constructor
     */
    public CLIView() {
    }

    @Override
    public void setSendMessageToServer(SendMessageToServer sendMessageToServer) {
        this.sendMessageToServer = sendMessageToServer;
    }

    @Override
    public void welcome() {
        Thread thread = new Thread(() -> {
            clearScreen();
            System.out.println("Insert a username: ");
            String username = inputString();
            clearScreen();
            System.out.println("Hi " + username);
            System.out.println("Press [ 1 ] to play");
            inputOne();
            System.out.println("The game is starting...");
            sendMessageToServer.joinGame(username);
        });
        thread.setDaemon(true);
        thread.start();
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
        String s = "  3 6 9 12 15 18 21 24 27 30 33 36";
        s += "\n\r0 2 5 8 11 14 17 20 23 26 29 32 35";
        s += "\n\r  1 4 7 10 13 16 19 21 25 28 31 34\n\r";
        System.out.println(s);
        System.out.println("[ 0 to 36 ] the respective wheel's numbers");
        System.out.println("[ 37 ] the numbers from 1 to 12");
        System.out.println("[ 38 ] the numbers from 13 to 24");
        System.out.println("[ 39 ] the numbers from 25 to 36");
        System.out.println("[ 40 ] the numbers from 1 to 18");
        System.out.println("[ 41 ] the numbers from 19 to 36");
        System.out.println("[ 42 ] the black numbers");
        System.out.println("[ 43 ] the red numbers");
        System.out.println("[ 44 ] the even numbers");
        System.out.println("[ 45 ] the odd numbers");
        System.out.println("[ 46 ] the first column");
        System.out.println("[ 47 ] the second column");
        System.out.println("[ 48 ] the third column\n\r");
    }

    @Override
    public void showResults(int winnerNumber, float winningCash) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("The winning number is: " + winnerNumber);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (winningCash > 0) {
                    System.out.println("You win " + winningCash + "$");
                } else {
                    System.out.println("You didn't win");
                }

            }
        };
        thread.setDaemon(true);
        thread.start();

    }

    public void doABet(DoABetMessage doABetMessage) {
        Thread threadDoABet = new Thread() {

            long timer = doABetMessage.EndTimer - System.currentTimeMillis();
            //boolean canBet = true;
            Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Time's up!");
                    sendMessageToServer.sendBet(doABetMessage);
                    //canBet = false;
                    canInput = false; 
                    //input.close();
                    
                                       
                    t.cancel();
                    currentThread().stop();
                }
            };

            @Override
            public void run() {
                //input = new Scanner(System.in);
                canInput = true;
                //canBet = true;                
                t.schedule(tt, timer);
                clearScreen();
                System.out.println("Time remaining " + Math.round(timer / 1000) + " seconds");
                while (canInput) {
                    try {
                        System.out.println("Avaliable credit: " + doABetMessage.playerState.cash + "$");
                        System.out.println("Press [ 1 ] to bet or [ 0 ] to exit");
                        //if(canInput==false) throw new Exception(); 
                        inputOne();
                        //if(canInput==false) throw new Exception();
                        clearScreen();
                        printBoard();
                        System.out.println("Do a bet:");
                        //if(canInput==false) throw new Exception();
                        if (doABetMessage.addBet(inputABet()) == false) {
                            //if(canInput==false) throw new Exception();
                            System.out.println("Insufficient credit!");
                            System.out.println("Avaliable credit: " + doABetMessage.playerState.cash + "$");
                        } else {
                            System.out.println("Successful bet");
                            //if(canInput==false) throw new Exception();
                        }
                    } catch (Exception e) {
                        canInput = false;
                    }
                }
                t.cancel();
            }

        };
        threadDoABet.setDaemon(true);
        threadDoABet.start();
        /*
         * /*
         * Timer t = new Timer();
         * TimerTask tt = new TimerTask() {
         * 
         * @Override
         * public void run() {
         * try {
         * if (timer > 0) {
         * if (timer % 5 == 0)
         * System.out.println("Time remaining " + timer + " seconds");
         * //timer--;
         * } else {
         * clearScreen();
         * System.out.println("Time's up");
         * t.cancel();
         * 
         * threadDoABet.stop();
         * sendMessageToServer.sendBet(playerState);
         * 
         * }
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         * 
         * }
         * };
         * t.scheduleAtFixedRate(tt, 0, 1000);
         */

        /*
         * Thread threadTimer = new Thread() {
         * 
         * @Override
         * public void run() {
         * Timer timer = new Timer();
         * RemindTask remindTask = new RemindTask(seconds, threadDoABet);
         * timer.schedule(remindTask, 0, 1000);
         * while (true) {
         * if (remindTask.isRunning == false) {
         * timer.cancel();
         * break;
         * }
         * }
         * 
         * DoABetMessage doABetMessage = new DoABetMessage();
         * doABetMessage.playerState = playerState;
         * sendMessageToServer.sendBet(new DoABetMessage());
         * }
         * };
         */

    }

    @Override
    public void closeBets() {
        Thread thread = new Thread(() -> {
            clearScreen();
            System.out.flush();
            System.out.println("Bets are off");
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void inputOne() {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        intInputValue = inputNumber();
        while (intInputValue != 1 && canInput) {
            System.out.println("Repeat, the input is wrong");
            intInputValue = inputNumber();
        }
    }

    private String inputString() {
        String stringValue = input.nextLine();
        return stringValue;
    }

    private int inputYesOrNo() {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        intInputValue = inputNumber();
        while (intInputValue != 1 && intInputValue != 0) {
            System.out.println("Repeat, the input is wrong");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }

    private Bet inputABet() {

        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("What box?");
        int intInputBox;
        intInputBox = inputNumber();
        while ((intInputBox < 0 || intInputBox > 48) && canInput) {
            System.out.println("Repeat, the input is wrong");
            intInputBox = inputNumber();
        }
        Box box = new Box(intInputBox);

        System.out.println("How much money?");
        int intInputCash;
        intInputCash = inputNumber();
        while ((intInputCash < 10 || intInputCash > 1000) && canInput) {
            System.out.println("Repeat, the input is wrong");
            intInputCash = inputNumber();
        }
        Bet bet = new Bet();
        bet.setBox(box);
        bet.setMoney(intInputCash);
        if (canInput)
            return bet;
        else
            return null;
    }

    private int inputNumber() {
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
                if (intInputValue == 0) {
                    sendMessageToServer.sendResetConnection();
                }
                return intInputValue;
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, repeat");
            }
        }
    }

}
