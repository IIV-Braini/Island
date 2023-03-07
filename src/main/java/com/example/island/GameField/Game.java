package com.example.island.GameField;

import java.util.concurrent.*;


public class Game {
    private static Game INSTANCE;
    private volatile boolean isGameStopped = true;
    private final ScheduledExecutorService scheduledExecutorService;
    private final int millisecondTurn;
    private Field field;


    public static void create(int millisecondTurn) {
        INSTANCE = new Game(millisecondTurn);
    }

    private Game(int millisecondTurn) {
        this.millisecondTurn = millisecondTurn;
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public void setField(Field field) {
        this.field = field;
    }

    public static synchronized void startGame() {
        INSTANCE.startInstanceGame();
    }

    private void startInstanceGame() {
        isGameStopped = false;
        scheduledExecutorService.scheduleWithFixedDelay(field, INSTANCE.millisecondTurn, INSTANCE.millisecondTurn, TimeUnit.MILLISECONDS);
    }

    public static void stopGame() {
        INSTANCE.stopInstanceGame();
        INSTANCE = null;
    }

    private void stopInstanceGame() {
        isGameStopped = true;
        scheduledExecutorService.shutdown();
        scheduledExecutorService.close();
        Field.clearField();
    }

    public static boolean isGameStopped() {
        return INSTANCE == null || INSTANCE.isGameStopped;
    }

    public static Game getInstance() {
        return INSTANCE;
    }

}
