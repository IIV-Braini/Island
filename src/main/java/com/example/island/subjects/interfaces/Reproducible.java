package com.example.island.subjects.interfaces;

import com.example.island.Creator.Creator;
import com.example.island.GameField.Cell;
import com.example.island.subjects.Animal;

public interface Reproducible {


    default void reproduction() {
        Animal animal = (Animal) this;
        Cell cell = animal.getCell();
        Animal kid = null;
        synchronized (cell) {
            if (animal.isReadyToBreed() && cell.checkFreeSlot(this.getClass()) && cell.checkCouple(animal)) {
                Animal animal1 = searchCouple();
                kid = breed(animal1);
            } else reloadReproductionsFunction();
        }
        if(kid != null) cell.addSubjectThisCell(kid);
    }


    private Animal breed(Animal animal) {
        Animal animal1 = (Animal) this;
        Animal kid = (Animal) Creator.getInstance().createNewSubject(this.getClass());
        kid.setReadyToBreed(false);
        kid.setCountTurnAfterBreed(0);
        animal1.setReadyToBreed(false);
        animal.setReadyToBreed(false);
        return kid;
    }


    private Animal searchCouple() {
        Animal animal = (Animal) this;
        return animal.getCell().getAnimalForBreed(animal);
    }


    private void reloadReproductionsFunction() {
        Animal animal = (Animal) this;
        if (!animal.isReadyToBreed()) {
            int turnAfterBreedForReloadFunction = 5;
            if (animal.getCountTurnAfterBreed() == turnAfterBreedForReloadFunction) {
                animal.setCountTurnAfterBreed(0);
                animal.setReadyToBreed(true);
            } else {
                animal.setCountTurnAfterBreed(animal.getCountTurnAfterBreed() + 1);
            }
        }
    }
}
