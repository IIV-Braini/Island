package com.example.island.subjects.herbivores;

import com.example.island.Creator.Config;
import com.example.island.Creator.Creator;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Herbivore;
import com.example.island.subjects.Predator;


public class Duck extends Herbivore {

    private Predator predator;
    public Duck(Config config) {
        super(config);
        if(!config.getHuntersGene().isEmpty())
            predator = Creator.getInstance().createPredator(config);
    }

    @Override
    public void eat() {
        predator.eat();
        super.eat();
    }
}
