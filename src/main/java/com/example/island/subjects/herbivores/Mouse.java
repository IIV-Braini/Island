package com.example.island.subjects.herbivores;

import com.example.island.Creator.Config;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Herbivore;
import com.example.island.subjects.Predator;
import com.example.island.subjects.interfaces.Huntable;

public class Mouse extends Herbivore implements Huntable {

    public Mouse(Config config) {
        super(config);
    }

}
