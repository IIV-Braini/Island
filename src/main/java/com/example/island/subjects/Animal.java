package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.Creator.Creator;
import com.example.island.GameField.Direction;
import com.example.island.GameField.Cell;
import com.example.island.subjects.interfaces.Movable;
import com.example.island.subjects.interfaces.Reproducible;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal extends Subject implements Reproducible, Movable, Callable<Void> {
    private volatile boolean isAlive = true;
    private volatile boolean isReadyToBreed = true;
    private int countTurnAfterBreed = 0;
    private double weight;
    private final double STANDARD_WEIGHT;
    private final int MAX_SPEED;
    private final double MAX_WEIGHT_EAT;
    private final String name;
    private Cell cell;


    private static final AtomicInteger atomicInteger = new AtomicInteger();

/*    public Animal(int speed, double weight, double MAX_WEIGHT_EAT) {
        name = getClass().getSimpleName() + " №" + atomicInteger.incrementAndGet();
        this.weight = weight;
        MAX_SPEED = speed;
        this.MAX_WEIGHT_EAT = MAX_WEIGHT_EAT;
        STANDARD_WEIGHT = weight;
    }*/

    public Animal(Config config) {
        name = config.getName()  + " №" + atomicInteger.incrementAndGet();
        weight = config.getStandardWeight();
        MAX_SPEED = config.getMaxSpeed();
        MAX_WEIGHT_EAT = config.getMaxEat();
        STANDARD_WEIGHT = weight;
        if(!Cell.checkMaxPopulationThisClass(this.getClass())){
            Cell.addMaxPopulationClass(Map.of(this.getClass(), config.maxCount));
        }
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
    public Void call() throws Exception {
        if (isAlive) {
            eat();
            reproduction();
            move();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void move() {
        if (MAX_SPEED != 0) {
            Cell currentCell = cell;
            currentCell.removeAnimals(this);
            Cell newCell;
            do {
                Direction direction = getRandomDirection();
                Integer step = getRandomStep();
                newCell = cell.getNewCell(direction, step);
            } while (!newCell.checkFreeSlot(this.getClass()));
            newCell.addAnimalThisCell(this);
            //System.out.println(name + " закончил ход. он пришел из " + currentCell.getName() + " в " + newCell.getName() + " " + Thread.currentThread().getName());
        }
    }

    public void deadAnimal() {
        isAlive = false;
        cell.removeAnimals(this);
    }

    private int getRandomStep() {
        return ThreadLocalRandom.current().nextInt(1, this.MAX_SPEED + 1);
    }

    private Direction getRandomDirection() {
        return switch (ThreadLocalRandom.current().nextInt(0, 4)) {
            case 1 -> Direction.DOWN;
            case 2 -> Direction.LEFT;
            case 3 -> Direction.RIGHT;
            default -> Direction.UP;
        };
    }


    public boolean isHungry() {
        double percentToHungry = 0.93;
        return STANDARD_WEIGHT - weight < STANDARD_WEIGHT * percentToHungry;
    }

    private void lossWeight() {
        weight = weight - (STANDARD_WEIGHT * 0.05); // теряет 5% веса
    }

    public double gainWeight(double food) {
        if (food > MAX_WEIGHT_EAT) {
            weight += MAX_WEIGHT_EAT;
        } else {
            weight += food;
        }
        return food - MAX_WEIGHT_EAT;
    }

    @Override
    public void reproduction() {
        //System.out.println(name + " запускает репродукцию. Его статут" + isReadyToBreed + " и кол-во до ближашейго брида " + countTurnAfterBreed);
        synchronized (cell) {
            if (isReadyToBreed && cell.checkFreeSlot(this.getClass()) && cell.checkCouple(this)) {
                Animal animal = searchCouple();
                Animal kid = breed(animal);
                //System.out.println(name + " нашел пару " + animal.getName() + " родили " + kid.getName());
                cell.addAnimalThisCell(kid);
            } else reloadReproductionsFunction();
        }

    }

    @Override
    public Animal breed(Animal animal) {
        Animal kid = Creator.getInstance().createNewAnimal(this.getClass());
        kid.isReadyToBreed = false;
        kid.countTurnAfterBreed = 0;
        isReadyToBreed = false;
        animal.isReadyToBreed = false;
        return kid;
    }

    @Override
    public Animal searchCouple() {
        return getCell().getAnimalForBreed(this);
    }

    @Override
    public void reloadReproductionsFunction() {
        if(isReadyToBreed == false) {
            int turnAfterBreedForReloadFunction = 5;
            if (countTurnAfterBreed == turnAfterBreedForReloadFunction) {
                countTurnAfterBreed = 0;
                isReadyToBreed = true;
            } else {
                countTurnAfterBreed++;
            }
        }
    }

    public abstract void eat();

    public boolean isAlive() {
        return isAlive;
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isReadyToBreed() {
        return isReadyToBreed;
    }

}
