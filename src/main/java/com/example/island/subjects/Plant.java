package com.example.island.subjects;

import com.example.island.GameField.Cell;

public class Plant extends Subject {
    private double weight;

    public void growthPlant() {
        double percentageOfGrowthPerTurn = 1.1;
        weight *= percentageOfGrowthPerTurn;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Plant(double weight) {
        this.weight = weight;
    }

    @Override
    public Void call() throws Exception {
        growthPlant();
        return null;
    }
}
