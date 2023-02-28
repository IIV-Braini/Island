package com.example.island.subjects.herbivores;

import com.example.island.Creator.Config;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Herbivore;
import com.example.island.subjects.interfaces.Huntable;


public class Caterpillar extends Herbivore implements Huntable {

    public Caterpillar (Config config) {
        super(config);
    }


    @Override
    public boolean hunt(Animal victim) {
        return false;
    }
}