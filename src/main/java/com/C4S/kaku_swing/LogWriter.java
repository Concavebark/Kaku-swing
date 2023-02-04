package com.C4S.kaku_swing;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogWriter {

    public String fileHandlerPattern = "kaku.log"; // Probably should double-check this is clear for release

    private Logger logger = Logger.getLogger("kakuLog");

    private FileHandler fileHandler;

    public void logInfo(String message) {
        try{
            fileHandler = new FileHandler(fileHandlerPattern, true);
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            logger.info(message);
        } catch (IOException e) {
            logger.throwing("LogWriter", "logInfo", e);
            throw new RuntimeException(e);
        }
    }

    public void logWarning(String message) {
        try {
            fileHandler = new FileHandler(fileHandlerPattern, true);
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            logger.warning(message);

            } catch (IOException e) {
            logger.throwing("LogWriter", "logWarning", e);
            throw new RuntimeException(e);
        }
    }

    public void logSevere(String message) {
        try {
            fileHandler = new FileHandler(fileHandlerPattern, true);
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            logger.severe(message);
        } catch (IOException e) {
            logger.throwing("LogWriter", "logSevere", e);
            throw new RuntimeException(e);
        }
    }

    public void logThrowing(String sourceClass, String sourceMethod, Throwable thrown) {
        try {
            fileHandler = new FileHandler(fileHandlerPattern, true);
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            logger.throwing(sourceClass, sourceMethod, thrown);
        } catch (IOException e) {
            logger.throwing("LogWriter", "logThrowing", e);
            throw new RuntimeException(e);

        }
    }
}
