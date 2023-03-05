package com.example.island.subjects.interfaces;

import com.example.island.GameField.Cell;
import com.example.island.GameField.Direction;
import com.example.island.subjects.Animal;

import java.util.concurrent.ThreadLocalRandom;

public interface Movable {

    default void move() {
        Animal animal = (Animal) this;
        if (animal.getMAX_SPEED() != 0) {
            Cell currentCell = animal.getCell();
            currentCell.removeSubject(animal);
            Cell newCell;
            do {
                Direction direction = getRandomDirection();
                Integer step = getRandomStep();
                newCell = currentCell.getNewCell(direction, step);
            } while (!newCell.checkFreeSlot(this.getClass()));
            newCell.addSubjectThisCell(animal);
            //System.out.println(animal.getName() + " закончил ход. он пришел из " + currentCell.getName() + " в " + newCell.getName() + " " + Thread.currentThread().getName());
        }
    }

    private int getRandomStep() {
        return ThreadLocalRandom.current().nextInt(1, ((Animal) this).getMAX_SPEED() + 1);
    }

    private Direction getRandomDirection() {
        return switch (ThreadLocalRandom.current().nextInt(0, 4)) {
            case 1 -> Direction.DOWN;
            case 2 -> Direction.LEFT;
            case 3 -> Direction.RIGHT;
            default -> Direction.UP;
        };
    }


}
