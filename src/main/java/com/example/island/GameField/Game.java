package com.example.island.GameField;

import com.example.island.Creator.Creator;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Herbivore;
import com.example.island.subjects.Predator;
import com.example.island.subjects.Subject;
import java.util.*;
import java.util.concurrent.*;


public class Game {
    private static Game INSTANCE;
    public static volatile boolean isGameStopped = false;
    private static ScheduledExecutorService scheduledExecutorService;
    private static int millisecondTurn;
    private final boolean endAfterAllAnimalDead;
    private final boolean endAfterAllPredatorsDead;
    private final boolean endAfterAllHerbivoreDead;
    private final boolean endAfterCountTurn;
    private int countTurnToStop;

    public static void create(int width, int height,
                              HashMap<Class<?>, Integer> map,
                              int millisecondTurn,
                              boolean endAfterAllAnimalDead,
                              boolean endAfterAllPredatorsDead,
                              boolean endAfterAllHerbivoreDead,
                              boolean endAfterCountTurn,
                              Integer countTurn) {
        INSTANCE = new Game(width, height, map, millisecondTurn,
                endAfterAllAnimalDead,
                endAfterAllPredatorsDead,
                endAfterAllHerbivoreDead,
                endAfterCountTurn,
                countTurn);
    }

    private Game(int width, int height, HashMap<Class<?>, Integer> map,
                 int millisecondTurn, boolean endAfterAllAnimalDead,
                 boolean endAfterAllPredatorsDead, boolean endAfterAllHerbivoreDead,
                 boolean endAfterCountTurn, Integer countTurn)
    {
        Field.createField(width, height);
        createSubjects(map);
        this.millisecondTurn = millisecondTurn;
        this.endAfterAllAnimalDead = endAfterAllAnimalDead;
        this.endAfterAllPredatorsDead = endAfterAllPredatorsDead;
        this.endAfterAllHerbivoreDead = endAfterAllHerbivoreDead;
        this.endAfterCountTurn = endAfterCountTurn;
        countTurnToStop = endAfterCountTurn ? countTurn : -1;
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public static synchronized void startGame() {
        System.out.print("\033[H\033[2J");
        isGameStopped = false;
        scheduledExecutorService.scheduleWithFixedDelay(Field.getInstance(), millisecondTurn, millisecondTurn, TimeUnit.MILLISECONDS);
    }

    public static void stopGame() {
        isGameStopped = true;
        scheduledExecutorService.shutdown();
        scheduledExecutorService.close();
        Field.clearField();
        Subject.reloadNumerator();
    }

    public static Game getInstance() {
        return INSTANCE;
    }


    private void createSubjects(Map<Class<?>, Integer> createAnimalsMap) {
        // Добавить проверку, если животных больше чем 80 доступных мест, урезать их кол-во
        HashSet<Subject> subjects = new HashSet<>();
        Set<Class<?>> classes = createAnimalsMap.keySet();
        for (Class<?> aClass : classes) {
            subjects.addAll(Creator.getInstance().createMultipleAnimals(aClass, createAnimalsMap.get(aClass)));
        }
        Field.getInstance().setSubjects(subjects);
    }

    public void checkGoals(HashSet<Subject> subjects) {
        if (endAfterAllAnimalDead && subjects.stream().noneMatch(s -> s instanceof Animal)) {
            System.out.println("Все животные умерли");
            stopGame();
        }
        if (endAfterAllPredatorsDead && subjects.stream().noneMatch(s -> s instanceof Predator)) {
            System.out.println("Все хищники умерли");
            stopGame();
        }
        if (endAfterAllHerbivoreDead && subjects.stream().noneMatch(s -> s instanceof Herbivore)) {
            System.out.println("Все травоядные умерли");
            stopGame();
        }
        if (endAfterCountTurn && countTurnToStop == Field.getInstance().getTurn()) {
            System.out.println("Кол-во ходом: " + countTurnToStop);
            stopGame();
        }
    }
}
