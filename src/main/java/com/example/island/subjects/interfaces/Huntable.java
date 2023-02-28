package com.example.island.subjects.interfaces;

import com.example.island.subjects.Animal;

import java.util.Map;
import java.util.Set;

public interface Huntable {

    void eat();

    boolean hunt(Animal victim);
}
