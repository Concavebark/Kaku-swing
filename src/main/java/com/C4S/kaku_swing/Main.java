package com.C4S.kaku_swing;

public class  Main {
    private static LogWriter logWriter = new LogWriter();
    private static int width = 400;
    private static int height = 400;

    public static void main(String[] args) { // could probably do some shitty logic here's with how I've implemented GUI

        new MainMenu("Kaku-swing Chess", width, height);
        logWriter.log("Launching Kaku-Swing chess engine", logLevels.INFO);

    }
}
