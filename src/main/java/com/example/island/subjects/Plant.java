package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.GameField.Cell;
import com.example.island.subjects.interfaces.Growable;

import java.util.concurrent.Callable;

public class Plant extends Subject implements Growable {

    public Plant(Config config) {
        super(config);
    }

}
