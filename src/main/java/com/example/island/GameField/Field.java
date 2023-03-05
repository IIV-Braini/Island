package com.example.island.GameField;

import com.example.island.subjects.*;
import com.example.island.subjects.herbivores.*;
import com.example.island.subjects.predators.*;

import java.util.HashSet;
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
    private AtomicInteger turn = new AtomicInteger(0);
    private ExecutorService executorService;


    private Field(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        cells = new Cell[WIDTH][HEIGHT];
        executorService = Executors.newFixedThreadPool(4);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                cells[x][y] = new Cell("Cell x = " + (x) + " , y = " + (y));
            }
        }
        setNeighbors(cells);
    }

    public static synchronized void createField(int width, int height) {
        INSTANCE = new Field(width, height);
    }

    public static synchronized Field getInstance() {
        return INSTANCE;
    }

    public static synchronized void clearField() {
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

    public Cell getRandomCell() {
        return cells[ThreadLocalRandom.current().nextInt(0, WIDTH)][ThreadLocalRandom.current().nextInt(0, HEIGHT)];
    }

    @Override
    public void run() {
        if (!checkGoals() && !Game.isGameStopped) {
            try {
                executorService.invokeAll(subjects);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            setSubjectsFromCells();
            showLog();
        } else {
            executorService.shutdown();
            executorService.close();
        }
    }


    private void setSubjectsFromCells() {
        HashSet<Subject> subjectsInAllCells = new HashSet<>();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                subjectsInAllCells.addAll(cells[x][y].getSubjectsHashSet());
            }
        }
        subjects = subjectsInAllCells;
    }

    private boolean checkGoals() {
        Game.getInstance().checkGoals(subjects);
        return Game.isGameStopped;
    }

    public int getTurn() {
        return turn.get();
    }

    private void showLog() {
        System.out.println("Ход номер: " + turn.getAndIncrement());
        System.out.println("На острове сейчас животных: " + subjects.stream().filter(s -> s instanceof Animal).toList().size() + "\n" +
                "из них Хищники: " + subjects.stream().filter(s -> s instanceof Predator).toList().size() + "\n" +
                "из них Травоядные: " + subjects.stream().filter(s -> s instanceof Herbivore).toList().size() + "\n" +
                "Травы на острове: " + subjects.stream().filter(s -> s instanceof Plant).toList().size());
        System.out.println("🐃" + subjects.stream().filter(s -> s instanceof Buffalo).toList().size());
        System.out.println("🐻" + subjects.stream().filter(s -> s instanceof Bear).toList().size());
        System.out.println("🐎" + subjects.stream().filter(s -> s instanceof Horse).toList().size());
        System.out.println("🦌" + subjects.stream().filter(s -> s instanceof Deer).toList().size());
        System.out.println("🐗" + subjects.stream().filter(s -> s instanceof WildBoar).toList().size());
        System.out.println("🐑" + subjects.stream().filter(s -> s instanceof Sheep).toList().size());
        System.out.println("🐐" + subjects.stream().filter(s -> s instanceof Goat).toList().size());
        System.out.println("🐺" + subjects.stream().filter(s -> s instanceof Wolf).toList().size());
        System.out.println("🐍" + subjects.stream().filter(s -> s instanceof Boa).toList().size());
        System.out.println("🦊" + subjects.stream().filter(s -> s instanceof Fox).toList().size());
        System.out.println("🦅" + subjects.stream().filter(s -> s instanceof Eagle).toList().size());
        System.out.println("🐇" + subjects.stream().filter(s -> s instanceof Rabbit).toList().size());
        System.out.println("🦆" + subjects.stream().filter(s -> s instanceof Duck).toList().size());
        System.out.println("🐁" + subjects.stream().filter(s -> s instanceof Mouse).toList().size());
        System.out.println("🐛" + subjects.stream().filter(s -> s instanceof Caterpillar).toList().size());
    }

    public void setSubjects(HashSet<Subject> subjects) {
        this.subjects = subjects;
    }
}

