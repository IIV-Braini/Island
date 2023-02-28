package com.example.island.subjects;

import com.example.island.Creator.Config;
import com.example.island.GameField.Cell;
import com.example.island.subjects.interfaces.Huntable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal implements Huntable {


    private static Map<String, Double> HUNTERS_GENE;
    private static Set<String> VICTIMS_CLASSES;


    public Predator(Config config) {
        super(config);
        initializeHuntersGene(config);
    }

    @Override
    public void eat() {
        Cell cell = getCell();
        synchronized (cell) {
            if (cell.checkVictimsForHunting(VICTIMS_CLASSES)) {
                Animal victim = cell.getAnimalForHunting(VICTIMS_CLASSES);
                if(hunt(victim)){
                    System.out.println(getName() + " поймал и съел " + victim.getName() + " в ячейке " + cell.getName() + " " + Thread.currentThread().getName());
                } else {
                    System.out.println(getName() + " охотился но не поймал " + victim.getName() + " в ячейке " + cell.getName() + " " + Thread.currentThread().getName());
                }
            }
        }
    }

    private void initializeHuntersGene(Config config) {
        if (HUNTERS_GENE == null) {
            HUNTERS_GENE = config.getHuntersGene();
            VICTIMS_CLASSES = HUNTERS_GENE.keySet();
        }
    }


    @Override
    public boolean hunt(Animal victim) {
        double chance = getChanceSuccess(victim.getClass().getSimpleName());
        if (chance > ThreadLocalRandom.current().nextDouble(0.0, 1.0)) {
            victim.deadAnimal();
            return true;
        }
        return false;
    }


    private double getChanceSuccess(String clazz) {
        return HUNTERS_GENE.get(clazz);
    }

}
