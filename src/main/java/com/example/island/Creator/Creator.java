package com.example.island.Creator;


import com.example.island.GameField.Cell;
import com.example.island.GameField.Field;
import com.example.island.subjects.*;
import com.example.island.subjects.herbivores.*;
import com.example.island.subjects.predators.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Creator {
    private static Creator INSTANCE;
    private final ConfigLoader configLoader;
    private Creator() {
        configLoader = new ConfigLoader();
    }

    public static synchronized Creator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Creator();
        }
        return INSTANCE;
    }

    public Subject createNewSubject(Class<?> clazz) {
        Config config;
        try {
            config = (configLoader.readConfig(clazz));
        } catch (IOException e) {
            System.out.println("Ошибка загрузки конфигурации");
            throw new RuntimeException(e);
        }
        return switch (clazz.getSimpleName()) {
            case "Wolf" -> new Wolf(config);
            case "Fox" -> new Fox(config);
            case "Eagle" -> new Eagle(config);
            case "Boa" -> new Boa(config);
            case "Bear" -> new Bear(config);
            case "Rabbit" -> new Rabbit(config);
            case "Buffalo" -> new Buffalo(config);
            case "Caterpillar" -> new Caterpillar(config);
            case "Deer" -> new Deer(config);
            case "Duck" -> new Duck(config);
            case "Goat" -> new Goat(config);
            case "Horse" -> new Horse(config);
            case "Mouse" -> new Mouse(config);
            case "Sheep" -> new Sheep(config);
            case "WildBoar" -> new WildBoar(config);
            case "Plant" -> new Plant(config);
            default -> throw new IllegalStateException("Unexpected value: " + clazz.getSimpleName());
        };
    }

    private Subject createNewSubjectWithRandomCell(Class<?> clazz){
        Subject subject = createNewSubject(clazz);
        Cell randomCell;
        do {
            randomCell = Field.getInstance().getRandomCell();
        }
        while (!randomCell.checkFreeSlot(subject.getClass()));
        randomCell.addSubjectThisCell(subject);
        return subject;
    }

    public HashSet<Subject> createMultipleAnimals(Class<?> clazz, int number){
        HashSet<Subject> subjects = new HashSet<>();
        for (int i = 0; i < number; i++) {
            Subject subject = createNewSubjectWithRandomCell(clazz);
            subjects.add(subject);
        }
        return subjects;
    }

    private class ConfigLoader {
        private final String pathDirectory;
        private final ObjectMapper mapper;

        private ConfigLoader() {
            mapper = new ObjectMapper();
            pathDirectory = "src\\main\\resources\\com\\example\\island\\SubjectsConfigs\\";
        }

        private Config readConfig(Class<?> typeSubject) throws IOException {
            String s = pathDirectory + typeSubject.getSimpleName() + ".json";
            File file = new File(s);
            return mapper.readValue(file, Config.class);
        }
    }
}
