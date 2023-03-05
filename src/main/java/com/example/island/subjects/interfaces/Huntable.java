package com.example.island.subjects.interfaces;

import com.example.island.subjects.Animal;
import com.example.island.subjects.Subject;

import java.util.concurrent.ThreadLocalRandom;


public interface Huntable {

    public default boolean hunt(Subject victim) {
        double chance = getChanceSuccess(victim.getClass().getSimpleName());
        if (chance > ThreadLocalRandom.current().nextDouble(0.0, 1.0)) {
            victim.deadSubject();
            return true;
        }
        return false;
    }


    private double getChanceSuccess(String clazz) {
        return ((Animal) this).getEdibleGene().get(clazz);
    }
}
