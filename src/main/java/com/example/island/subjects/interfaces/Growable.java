package com.example.island.subjects.interfaces;

import com.example.island.subjects.Plant;

public interface Growable {

    default void grow() {
        Plant plant = (Plant) this;
        double percentageOfGrowthPerTurn = 1.1;
        plant.setWeight(plant.getWeight() * percentageOfGrowthPerTurn);
    }
}
