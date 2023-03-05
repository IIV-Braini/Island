package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.subjects.interfaces.Movable;
import com.example.island.subjects.interfaces.Reproducible;
import com.example.island.subjects.interfaces.toEating;
import java.util.Map;
import java.util.Set;



public abstract class Animal extends Subject implements Reproducible, Movable, toEating {

    private final Map<String, Double> EDIBLE_GENE;
    private final Set<String> EDIBLE_CLASSES;
    private volatile boolean isReadyToBreed;
    private int countTurnAfterBreed;
    private final double STANDARD_WEIGHT;
    private final int MAX_SPEED;
    private final double MAX_WEIGHT_EAT;

    public Map<String, Double> getEdibleGene() {
        return EDIBLE_GENE;
    }

    public Set<String> getEdibleClasses() {
        return EDIBLE_CLASSES;
    }

    public Animal(Config config) {
        super(config);
        MAX_SPEED = config.getMaxSpeed();
        MAX_WEIGHT_EAT = config.getMaxEat();
        STANDARD_WEIGHT = config.getStandardWeight();
        isReadyToBreed = true;
        EDIBLE_GENE = config.getHuntersGene();
        EDIBLE_CLASSES = EDIBLE_GENE.keySet();
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public boolean isHungry() {
        double percentToHungry = 0.93;
        return STANDARD_WEIGHT - getWeight() < STANDARD_WEIGHT * percentToHungry;
    }

    @Override
    public Void call() throws Exception {
        lossWeightOnTurn();
        if(getWeight() <= 0) deadSubject();
        return super.call();
    }

    public double gainWeight(double food) {
        double weight;
        if (food > MAX_WEIGHT_EAT) {
            weight = getWeight() + MAX_WEIGHT_EAT;
        } else {
            weight = getWeight() + food;
        }
        setWeight(weight);
        return food - MAX_WEIGHT_EAT;
    }

    public boolean isReadyToBreed() {
        return isReadyToBreed;
    }

    public int getCountTurnAfterBreed() {
        return countTurnAfterBreed;
    }

    public void setCountTurnAfterBreed(int countTurnAfterBreed) {
        this.countTurnAfterBreed = countTurnAfterBreed;
    }

    public void setReadyToBreed(boolean readyToBreed) {
        isReadyToBreed = readyToBreed;
    }

    private void lossWeightOnTurn() {
        double percentToLossWeightOnTurn = 0.05;
        double newWeight = getWeight() - (STANDARD_WEIGHT * percentToLossWeightOnTurn);
        setWeight(newWeight);
    }
}
