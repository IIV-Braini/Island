package com.example.island.GameField;

import com.example.island.Creator.Creator;
import com.example.island.IslandApplication;
import com.example.island.subjects.*;
import com.example.island.subjects.herbivores.*;
import com.example.island.subjects.predators.*;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Field implements Runnable {

    private static Field INSTANCE;
    private static int WIDTH;
    private static int HEIGHT;
    private final Cell[][] cells;
    private volatile HashSet<Subject> subjects;
    private final AtomicInteger turn = new AtomicInteger(0);
    private final ExecutorService executorService;
    private final boolean endAfterAllAnimalDead;
    private final boolean endAfterAllPredatorsDead;
    private final boolean endAfterAllHerbivoreDead;
    private final boolean endAfterCountTurn;
    private final int countTurnToStop;


    private Field(int width, int height, boolean endAfterAllAnimalDead, boolean endAfterAllPredatorsDead, boolean endAfterAllHerbivoreDead, boolean endAfterCountTurn, Integer countTurn) {
        this.endAfterAllAnimalDead = endAfterAllAnimalDead;
        this.endAfterAllPredatorsDead = endAfterAllPredatorsDead;
        this.endAfterAllHerbivoreDead = endAfterAllHerbivoreDead;
        this.endAfterCountTurn = endAfterCountTurn;
        countTurnToStop = endAfterCountTurn ? countTurn : -1;
        WIDTH = width;
        HEIGHT = height;
        cells = new Cell[WIDTH][HEIGHT];
        executorService = Executors.newFixedThreadPool(4);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                cells[x][y] = new Cell();
            }
        }
        setNeighbors(cells);
    }

    public static synchronized void createField(int width, int height, Map<Class<?>, Integer> createAnimalsMap, boolean endAfterAllAnimalDead, boolean endAfterAllPredatorsDead, boolean endAfterAllHerbivoreDead, boolean endAfterCountTurn, Integer countTurn) {
        INSTANCE = new Field(width, height, endAfterAllAnimalDead, endAfterAllPredatorsDead, endAfterAllHerbivoreDead, endAfterCountTurn, countTurn);
        INSTANCE.subjects = Creator.getInstance().createSubjects(createAnimalsMap);
        Game.getInstance().setField(INSTANCE);
    }

    public static synchronized void clearField() {
        INSTANCE.closeExecutorService();
        INSTANCE = null;
    }

    private void setNeighbors(Cell[][] cells) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (y != 0) cells[x][y].setCellOnUp(cells[x][y - 1]);
                if (y != HEIGHT - 1) cells[x][y].setCellOnDown(cells[x][y + 1]);
                if (x != 0) cells[x][y].setCellOnLeft(cells[x - 1][y]);
                if (x != WIDTH - 1) cells[x][y].setCellOnRight(cells[x + 1][y]);
            }
        }
    }

    public static Cell getRandomCell() {
        return INSTANCE.cells[ThreadLocalRandom.current().nextInt(0, WIDTH)][ThreadLocalRandom.current().nextInt(0, HEIGHT)];
    }

    @Override
    public void run() {
        if (!Game.isGameStopped()) {
            checkGoals();
            try {
                executorService.invokeAll(subjects);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            reloadSubjects();
            IslandApplication.showMessage(createLogTurnToString());
        }
    }

    private void closeExecutorService() {
        executorService.shutdown();
        executorService.close();
    }

    private void reloadSubjects() {
        HashSet<Subject> subjectsInAllCells = new HashSet<>();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                subjectsInAllCells.addAll(cells[x][y].getSubjectsHashSet());
            }
        }
        subjects = subjectsInAllCells;
    }


    public void checkGoals() {
        if (endAfterAllAnimalDead && subjects.stream().noneMatch(s -> s instanceof Animal)) {
            IslandApplication.showMessage("Игра окончена.Все животные умерли");
            Game.stopGame();
        }
        if (endAfterAllPredatorsDead && subjects.stream().noneMatch(s -> s instanceof Predator)) {
            IslandApplication.showMessage("Игра окончена. Все хищники умерли");
            Game.stopGame();
        }
        if (endAfterAllHerbivoreDead && subjects.stream().noneMatch(s -> s instanceof Herbivore)) {
            IslandApplication.showMessage("Игра окончена. Все травоядные умерли");
            Game.stopGame();
        }
        if (endAfterCountTurn && countTurnToStop == turn.get()) {
            IslandApplication.showMessage("Игра окончена. Кол-во ходов: " + countTurnToStop);
            Game.stopGame();
        }
    }

    private String createLogTurnToString() {
        return "Ход номер: " + turn.getAndIncrement() + "\n" +
                "На острове сейчас животных: " + subjects.stream().filter(s -> s instanceof Animal).toList().size() + "\n" +
                "из них Хищники: " + subjects.stream().filter(s -> s instanceof Predator).toList().size() + ", " +
                " Травоядные: " + subjects.stream().filter(s -> s instanceof Herbivore).toList().size() + "\n" +
                "Травы на острове: " + subjects.stream().filter(s -> s instanceof Plant).toList().size() + "\n" +
                "🐃 = " + subjects.stream().filter(s -> s instanceof Buffalo).toList().size() + "\n" +
                "🐻 = " + subjects.stream().filter(s -> s instanceof Bear).toList().size() + "\n" +
                "🐎 = " + subjects.stream().filter(s -> s instanceof Horse).toList().size() + "\n" +
                "🦌 = " + subjects.stream().filter(s -> s instanceof Deer).toList().size() + "\n" +
                "🐗 = " + subjects.stream().filter(s -> s instanceof WildBoar).toList().size() + "\n" +
                "🐑 = " + subjects.stream().filter(s -> s instanceof Sheep).toList().size() + "\n" +
                "🐐 = " + subjects.stream().filter(s -> s instanceof Goat).toList().size() + "\n" +
                "🐺 = " + subjects.stream().filter(s -> s instanceof Wolf).toList().size() + "\n" +
                "🐍 = " + subjects.stream().filter(s -> s instanceof Boa).toList().size() + "\n" +
                "🦊 = " + subjects.stream().filter(s -> s instanceof Fox).toList().size() + "\n" +
                "🦅 = " + subjects.stream().filter(s -> s instanceof Eagle).toList().size() + "\n" +
                "🐇 = " + subjects.stream().filter(s -> s instanceof Rabbit).toList().size() + "\n" +
                "🦆 = " + subjects.stream().filter(s -> s instanceof Duck).toList().size() + "\n" +
                "🐁 = " + subjects.stream().filter(s -> s instanceof Mouse).toList().size() + "\n" +
                "🐛 = " + subjects.stream().filter(s -> s instanceof Caterpillar).toList().size() + "\n";
    }


}

