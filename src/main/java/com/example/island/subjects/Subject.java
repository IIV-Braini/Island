package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.GameField.Cell;
import com.example.island.subjects.interfaces.Growable;
import com.example.island.subjects.interfaces.Movable;
import com.example.island.subjects.interfaces.Reproducible;
import com.example.island.subjects.interfaces.toEating;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class Subject implements Callable<Void> {
    private volatile boolean isAlive;
    private double weight;
    private Cell cell;

    public Subject(Config config) {
        weight = config.getStandardWeight();
        isAlive = true;
        if (!Cell.checkMaxPopulationThisClass(this.getClass())) {
            Cell.addMaxPopulationClass(Map.of(this.getClass(), config.getMaxCount()));
        }
    }

    @Override
    public Void call() throws Exception {
            if (this instanceof toEating && isAlive) ((toEating) this).eat();
            if (this instanceof Movable && isAlive) ((Movable) this).move();
            if (this instanceof Reproducible && isAlive) ((Reproducible) this).reproduction();
            if (this instanceof Growable && isAlive) ((Growable) this).grow();
        return null;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public synchronized void deadSubject() {
        isAlive = false;
        cell.removeSubject(this);
    }
}
