package com.example.island.Creator;

import com.example.island.subjects.Plant;
import com.example.island.subjects.herbivores.Rabbit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public String name;
    public int maxSpeed;
    public double standardWeight;
    public double maxEat;
    public int maxCount;
    private HashMap<String, Double> huntersGene;


    public Config(@JsonProperty("name") String name,
                  @JsonProperty("maxSpeed") int maxSpeed,
                  @JsonProperty("standardWeight")double standardWeight,
                  @JsonProperty("maxEat")double maxEat,
                  @JsonProperty("maxCount")int maxCount,
                  @JsonProperty(value = "huntersGene") HashMap<String, Double> huntersGene) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.standardWeight = standardWeight;
        this.maxEat = maxEat;
        this.huntersGene = huntersGene;
        this.maxCount = maxCount;
    }

    public void setHuntersGene(HashMap<String, Double> huntersGene) {
        this.huntersGene = huntersGene;
    }


    public String getName() {
        return name;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getStandardWeight() {
        return standardWeight;
    }

    public double getMaxEat() {
        return maxEat;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Map<String, Double> getHuntersGene() {
        return huntersGene;
    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", standardWeight=" + standardWeight +
                ", maxEat=" + maxEat +
                ", maxCount=" + maxCount +
                '}';
    }
}
