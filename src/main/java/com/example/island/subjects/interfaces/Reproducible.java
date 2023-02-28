package com.example.island.subjects.interfaces;

import com.example.island.subjects.Animal;

public interface Reproducible {

    public void reproduction();

    public Animal breed(Animal animal);
    public Animal searchCouple();

    public void reloadReproductionsFunction();
}
