package com.example.island.Creator;


import com.example.island.GameField.Cell;
import com.example.island.GameField.Field;
import com.example.island.subjects.Animal;
import com.example.island.subjects.Herbivore;
import com.example.island.subjects.Plant;
import com.example.island.subjects.Predator;
import com.example.island.subjects.herbivores.Rabbit;
import com.example.island.subjects.predators.Wolf;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Creator {
    private static Creator INSTANCE;
    private ConfigLoader configLoader;
    private Creator() {
        configLoader = new ConfigLoader();
    }

    public static synchronized Creator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Creator();
        }
        return INSTANCE;
    }

    public Animal createNewAnimal(Class<?> clazz) {
        Config config;
        try {
            config = (configLoader.readConfig(clazz));
        } catch (IOException e) {
            System.out.println("Ошибка загрузки конфигурации");
            throw new RuntimeException(e);
        }
        return switch (clazz.getSimpleName()) {
            case "Wolf" -> new Wolf(config);
            case "Rabbit" -> new Rabbit(config);
            default -> throw new IllegalStateException("Unexpected value: " + clazz.getSimpleName());
        };
    }

    public Predator createPredator(Config config) {
        return new Predator(config) {
        };
    }

    private Config getConfig(String typeAnimal) {
        return null;
    }

    public Animal createNewAnimalWithRandomCell(Class<?> clazz, Field field){
        Animal animal = createNewAnimal(clazz);
        Cell randomCell;
        do {
            randomCell = field.getRandomCell();
        }
        while (!randomCell.checkFreeSlot(animal.getClass()));
        randomCell.addAnimalThisCell(animal);
        return animal;
    }

    public List<Animal> createMultipleAnimals(Class<?> clazz, int number, Field field){
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            animals.add(createNewAnimalWithRandomCell(clazz, field));
        }
        return animals;
    }

    public void createPlant (Cell cell) {
        cell.addPlant(new Plant(ThreadLocalRandom.current().nextDouble(0, 1)));
    }

    public void createRandomMultiplePlants (int count, Field field) {
        for (int i = 0; i < count; i++) {
            createPlant(field.getRandomCell());
        }
    }

    private class ConfigLoader {

        //private Path pathDirectory = Path.of("src\\main\\resources\\com\\example\\island\\SubjectsConfigs");
        private String pathDirectory = "src\\main\\resources\\com\\example\\island\\SubjectsConfigs\\";
        private ObjectMapper mapper = new ObjectMapper();

        private Config readConfig(Class<?> typeSubject) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            String s = pathDirectory + typeSubject.getSimpleName() + ".json";
            File file = new File(s);
            return mapper.readValue(file, Config.class);
        }
    }
}
