package com.example.island.GameField;

import com.example.island.subjects.Animal;
import com.example.island.Creator.Creator;
import com.example.island.subjects.herbivores.Rabbit;
import com.example.island.subjects.predators.Wolf;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    public static volatile boolean isGameStopped = false;
    public static final ExecutorService service = Executors.newFixedThreadPool(4);;

    public static List<Animal> animals;

    public static void main(String[] args) throws InterruptedException {



        Map<Class<?>, Integer> map = Map.of(
                Wolf.class, 20,
                Rabbit.class, 150
                );

        Game game = new Game();
        Field field = new Field(5, 3);

        animals = game.createAnimals(map, field);
        service.invokeAll(animals);
        field.printCountAllAnimals();
        /* service.invokeAll(animals);
        field.printCountAllAnimals();*/
        for (Class<?> aClass : Cell.MAX_COUNT_POPULATION.keySet()) {
            System.out.println(aClass + " " + Cell.MAX_COUNT_POPULATION.get(aClass));
        }
        service.shutdown();

    }



    private List<Animal> createAnimals(Map<Class<?>, Integer> createAnimalsMap, Field field) {
        // Добавить проверку, если животных больше чем 80 доступных мест, урезать их кол-во
        List<Animal> animals = new ArrayList<>();
        Set<Class<?>> classes = createAnimalsMap.keySet();
        for (Class<?> aClass : classes) {
            animals.addAll(Creator.getInstance().createMultipleAnimals(aClass, createAnimalsMap.get(aClass), field));
        }
        //
        return animals;
    }

    private void createPlant() {
        //Creator.getInstance().createPlantInEachCell(field);
    }
}
