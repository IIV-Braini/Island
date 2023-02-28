package com.example.island.GameField;

import com.example.island.subjects.Animal;
import com.example.island.subjects.Plant;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Cell {

    public static Map<Class<?>, Integer> MAX_COUNT_POPULATION = new HashMap<>();

    private final String name;
    private Cell cellOnUp;
    private Cell cellOnDown;
    private Cell cellOnLeft;
    private Cell cellOnRight;
    private int turn;


    //private volatile List<Animal> animalHashSet = new ArrayList<>();
    private volatile HashSet<Animal> animalHashSet = new HashSet<>();
    private PriorityQueue<Plant> plants = new PriorityQueue<>();

    public String getName() {
        return name;
    }

    public void printCountAllAnimalsInCount() {
        System.out.println("В ячейке " + name + " общее кол-во животных: " + animalHashSet.size());
    }

    public Cell(String name) {
        this.name = name;
    }

    public synchronized Cell getNewCell(Direction direction, Integer step) {
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


    private synchronized Cell getCellOnDirection(Direction direction) {
        return switch (direction) {
            case UP -> cellOnUp;
            case DOWN -> cellOnDown;
            case LEFT -> cellOnLeft;
            case RIGHT -> cellOnRight;
        };
    }

    public synchronized void addAnimalThisCell(Animal animal) {
        animalHashSet.add(animal);
        animal.setCell(this);
    }

    public synchronized boolean checkFreeSlot(Class<?> clazz) {
        int countClass = countCertainClassInCell(clazz);
        //System.out.println("В ячейке " + name + " кол-во " + clazz.getSimpleName() + " равно " + countClass + " а максимальное значение " + MAX_COUNT_POPULATION.get(clazz));
        return countClass < MAX_COUNT_POPULATION.get(clazz);
    }

    public static void addMaxPopulationClass (Map<Class<?>, Integer> maxPopulationClass) {
        MAX_COUNT_POPULATION.putAll(maxPopulationClass);
    }
    public static boolean checkMaxPopulationThisClass(Class<?> clazz){
        return MAX_COUNT_POPULATION.containsKey(clazz);
    }

    private synchronized int countCertainClassInCell(Class<?> clazz) {
        int count = 0;
        synchronized (animalHashSet) {
            for (Animal animal : animalHashSet) {
                if (animal.getClass() == clazz) count++;
            }
        }
        return count;
    }

    public synchronized void removeAnimals(Animal animal) {
        animalHashSet.remove(animal);
    }

    public boolean checkCouple(Animal animal) {
        for (Animal couple : animalHashSet) {
            if (animal.getClass() == couple.getClass() && animal != couple && couple.isReadyToBreed()) return true;
        }
        return false;
    }

    public Animal getAnimalForBreed(Animal animal) {
        List<Animal> couplesList = animalHashSet.stream().filter(couple -> couple != animal && couple.getClass() == animal.getClass() && couple.isReadyToBreed()).toList();
        return couplesList.get(ThreadLocalRandom.current().nextInt(0, couplesList.size()));
    }

    public boolean checkVictimsForHunting(Set<String> victimsClasses) {
        for (Animal animal : animalHashSet) {
            if (victimsClasses.contains(animal.getClass().getSimpleName())) return true;
        }
        return false;
    }

    public Animal getAnimalForHunting(Set<String> victimsClasses) {
        List<Animal> victimsList = animalHashSet.stream().filter(animal -> victimsClasses.contains(animal.getClass().getSimpleName()) && animal.isAlive()).toList();
        return victimsList.get(ThreadLocalRandom.current().nextInt(0, victimsList.size()));
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public boolean checkPlantFromCell() {
        return !plants.isEmpty();
    }

    public Plant getPlant() {
        return plants.poll();
    }
}
