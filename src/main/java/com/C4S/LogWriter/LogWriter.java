package com.C4S.LogWriter;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * Simple LogWriting class written by @Devin-LM
 */
public class LogWriter {
    private static final String FILE_NAME = "log.log";

    /***
     * Simple log method to write program logs to a file
     * @param message the text to be written to the log file
     * @throws RuntimeException if the file to be written to is in a directory that is inaccessible
     */
    public static void log(String message) {
        createFile(FILE_NAME);
        try {

            FileWriter logWriter = new FileWriter(FILE_NAME, true);

            logWriter.write(getFormattedDate() + " - [STANDARD] " + message + "\n");

            logWriter.close();

            System.out.println("Log writing successful.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /***
     * Simple log method to write program logs to a file
     * @param message the text to be written to the log file
     * @param level the level the message is supposed to be logged at, found in {@link LogLevels}
     * @see LogLevels
     * @throws RuntimeException if the file to be written to is in a directory that is inaccessible
     */
    public static void log(String message, @NotNull LogLevels level) {
        createFile(FILE_NAME);
        try {

            FileWriter logWriter = new FileWriter(FILE_NAME, true);

            logWriter.write(getFormattedDate() + " - [" + level + "] " + message + "\n");

            logWriter.close();

            System.out.println("Log writing successful.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /***
     * Simple log method to write program logs to a file
     * @param message the text to be written to the log file
     * @param level the level the message is supposed to be logged at, found in {@link LogLevels}
     * @param fileName the name of the log file to be written to
     * @see LogLevels
     * @throws RuntimeException if the file to be written to is in a directory that is inaccessible
     */
    public static void log(String message, @NotNull LogLevels level, String fileName) {
        createFile(fileName);
        try {

            FileWriter logWriter = new FileWriter(fileName, true);

            logWriter.write(getFormattedDate() + " - [" + level + "] " + message + "\n");

            logWriter.close();

            System.out.println("Log writing successful.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * A simple method to quickly create a file
     * @param fileName the name of the file to attempt to create
     * @throws RuntimeException if the file to be written to is in a directory that is inaccessible
     */
    private static void createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File Created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A simple internal method used for getting the date and time in a specific format
     * @return a formatted date and time string
     */
    private static String getFormattedDate() {
        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return dateTime.format(formatter);
    }
}
