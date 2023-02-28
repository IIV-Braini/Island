package com.example.island.GameField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Field {

    private List<Cell> cellsList = new ArrayList<>();
    private Cell[][] cells;

    //private volatile AtomicInteger turn = new AtomicInteger(0);


    public Field(int width, int height) {
        cells = new Cell[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y] = new Cell("Cell x = " + (x) + " , y = " + (y));
                //System.out.print(cells[x][y].getName() + " | ");
            }
            //System.out.println("");
        }
        setNeighbors(cells);
    }

    private void setNeighbors(Cell[][] cells) {
        int width = cells.length;
        int height = cells[0].length;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y != 0) cells[x][y].setCellOnUp(cells[x][y - 1]);
                if (y != height - 1) cells[x][y].setCellOnDown(cells[x][y + 1]);
                if (x != 0) cells[x][y].setCellOnLeft(cells[x - 1][y]);
                if (x != width - 1) cells[x][y].setCellOnRight(cells[x + 1][y]);
            }
        }
    }

    public void printCountAllAnimals() {
        int width = cells.length;
        int height = cells[0].length;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y].printCountAllAnimalsInCount();
            }
        }
    }

    public Cell getRandomCell() {
        return cells[ThreadLocalRandom.current().nextInt(0, cells.length)][ThreadLocalRandom.current().nextInt(0, cells[0].length)];
    }
}
