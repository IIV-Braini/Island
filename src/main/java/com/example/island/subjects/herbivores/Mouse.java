package com.example.island.subjects.herbivores;

import com.example.island.Creator.Config;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Predator;

public class Mouse extends Predator {


    public Mouse(Config config) {
        super(config);
    }

    @Override
    public void eat() {
        if(isHungry()) super.eat();
        if(isHungry()) {
            Animal animal = this; // ВЫЗЫВАЕТСЯ АБСТРАКТНЫЙ МЕТОД ЖИВОТНОГО, ПЕРЕДЕЛАТЬ, ЧТО БЫ ОХОТИЛСЯ.
            animal.eat();
        }
    }

}
