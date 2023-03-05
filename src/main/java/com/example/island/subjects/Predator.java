package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.GameField.Cell;
import com.example.island.subjects.interfaces.Huntable;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal implements Huntable {

    public Predator(Config config) {
        super(config);
    }
}
