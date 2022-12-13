package src.Casino.lib;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    private static long startingTime = -1;

    public static void setStartingTime(){
        startingTime = startingTime == -1 ? System.currentTimeMillis() : startingTime;
    }

    public static String getLogTime(){
        return String.format("\tElapsed time: %sms [%s]", Long.toString(getElapsedTime()), DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
    }

    public static long getElapsedTime(){
        return (System.currentTimeMillis() - startingTime)/1000000000;
    }
}