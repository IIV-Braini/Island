package com.example.island.GameField;

import com.example.island.subjects.Animal;
import com.example.island.subjects.Subject;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Cell {
    public static Map<Class<?>, Integer> MAX_COUNT_POPULATION = new HashMap<>();
    private final String name;
    private Cell cellOnUp;
    private Cell cellOnDown;
    private Cell cellOnLeft;
    private Cell cellOnRight;
    private volatile HashSet<Subject> subjectsHashSet = new HashSet<>();

    public String getName() {
        return name;
    }

    public HashSet<Subject> getSubjectsHashSet() {
        return subjectsHashSet;
    }

    public Cell(String name) {
        this.name = name;
    }

    public Cell getNewCell(Direction direction, Integer step) {
        if (getCellOnDirection(direction) == null) {
            return this;
        }
        if (step == 1) {
            return getCellOnDirection(direction);
        }
        return getCellOnDirection(direction).getNewCell(direction, step - 1);
    }

    public void setCellOnUp(Cell cellOnUp) {
        this.cellOnUp = cellOnUp;
    }

    public void setCellOnDown(Cell cellOnDown) {
        this.cellOnDown = cellOnDown;
    }

    public void setCellOnLeft(Cell cellOnLeft) {
        this.cellOnLeft = cellOnLeft;
    }

    public void setCellOnRight(Cell cellOnRight) {
        this.cellOnRight = cellOnRight;
    }


    private Cell getCellOnDirection(Direction direction) {
        return switch (direction) {
            case UP -> cellOnUp;
            case DOWN -> cellOnDown;
            case LEFT -> cellOnLeft;
            case RIGHT -> cellOnRight;
        };
    }

    public boolean checkFreeSlot(Class<?> clazz) {
        int countClass = countCertainClassInCell(clazz);
        return countClass < MAX_COUNT_POPULATION.get(clazz);
    }

    public static void addMaxPopulationClass(Map<Class<?>, Integer> maxPopulationClass) {
        MAX_COUNT_POPULATION.putAll(maxPopulationClass);
    }

    public static boolean checkMaxPopulationThisClass(Class<?> clazz) {
        return MAX_COUNT_POPULATION.containsKey(clazz);
    }

    public boolean checkCouple(Animal animal) {
        return subjectsHashSet.stream()
                .filter(o -> o.getClass() == animal.getClass())
                .filter(o -> o != animal)
                .map(c -> (Animal) c)
                .anyMatch(Animal::isReadyToBreed);
    }

    public Animal getAnimalForBreed(Animal animal) {
        List<Animal> animals = subjectsHashSet.stream()
                .filter(o -> o.getClass() == animal.getClass())
                .filter(o -> o != animal)
                .map(c -> (Animal) c)
                .filter(Animal::isReadyToBreed)
                .toList();
        return animals.get(ThreadLocalRandom.current().nextInt(0, animals.size()));
    }

    public void printCountAllSubjectsInCount() {
        System.out.println("В ячейке " + name + " общее кол-во животных: " + subjectsHashSet.size());
    }

    public synchronized void addSubjectThisCell(Subject subject) {
        subjectsHashSet.add(subject);
        subject.setCell(this);
    }

    private int countCertainClassInCell(Class<?> clazz) {
        int count = 0;
        synchronized (subjectsHashSet) {
            for (Subject subject : subjectsHashSet) {
                if (subject.getClass() == clazz) count++;
            }
        }
        return count;
    }

    public Subject getSubjectForEating(Set<String> edibleClasses) {
        List<? extends Subject> eatingList = subjectsHashSet.stream().filter(s -> edibleClasses.contains(s.getClass().getSimpleName()) && s.isAlive()).toList();
        return eatingList.get(ThreadLocalRandom.current().nextInt(0, eatingList.size()));
    }

    public boolean checkSubjectForEating(Set<String> edibleClasses) {
        for (Subject subject : subjectsHashSet) {
            if (edibleClasses.contains(subject.getClass().getSimpleName())) return true;
        }
        return false;
    }

    public synchronized void removeSubject(Subject subject) {
        subjectsHashSet.remove(subject);
    }

}
