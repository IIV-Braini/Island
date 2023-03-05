package com.example.island.subjects.interfaces;

import com.example.island.GameField.Cell;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Plant;
import com.example.island.subjects.Subject;

public interface toEating {

    public default void eat() {
        Animal animal = (Animal) this;
        Cell cell = animal.getCell();
        synchronized (cell) {
            if (cell.checkSubjectForEating(animal.getEdibleClasses()) && animal.isHungry()) {
                Subject food = cell.getSubjectForEating(animal.getEdibleClasses());
                if (food instanceof Plant) {
                    double remains = animal.gainWeight(food.getWeight());
                    if (remains <= 0) food.deadSubject();
                } else if (animal instanceof Huntable) {
                    Huntable hunter = (Huntable) this;
                    if (hunter.hunt(food)) {
                        animal.gainWeight(food.getWeight());
                    }
                }
            }
        }
    }


}
