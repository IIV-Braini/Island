package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.GameField.Cell;

public abstract class Herbivore extends Animal {

    public Herbivore(Config config) {
        super(config);
    }

    @Override
    public void eat() {
        Cell cell = getCell();
        synchronized (cell) {
            if (cell.checkPlantFromCell()) {
                Plant plant = cell.getPlant();
                double remainingWeight = this.gainWeight(plant.getWeight());
                if(remainingWeight > 0 ) {
                    plant.setWeight(remainingWeight);
                    cell.addPlant(plant);
                }
            }
        }
    }
}
