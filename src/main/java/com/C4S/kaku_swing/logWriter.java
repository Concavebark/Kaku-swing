package com.C4S.kaku_swing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * Simple LogWriting class written by @Devin-LM
 */
public class logWriter {
    private static String _fileName = "kakuLog.log";
    /*
    public LogWriter() {
        _fileName = "kakuLog.log";
    }

    public LogWriter(String fileName) {
        _fileName = fileName;
    }
    */

    public static void log(String message, logLevels level) {
        createFile(_fileName);
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);

            FileWriter logWriter = new FileWriter(_fileName, true);

            logWriter.write(formattedDateTime + " - [level] " + message);

            logWriter.close();

            System.out.println("Log writing successful.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeFileName(String fileName){

        _fileName = fileName;
    }

    private static void createFile(String fileName) {
        try {
            File logFile = new File(fileName);
            if (logFile.createNewFile()) {
                System.out.println("Log file Created: " + logFile.getName());
            } else {
                System.out.println("Log file already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
