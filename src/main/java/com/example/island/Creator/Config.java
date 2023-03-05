package com.example.island.Creator;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private String name;
    private int maxSpeed;
    private double standardWeight;
    private double maxEat;
    private int maxCount;
    private HashMap<String, Double> edibleGene;

    public Config(@JsonProperty("name") String name,
                  @JsonProperty("maxSpeed") int maxSpeed,
                  @JsonProperty("standardWeight")double standardWeight,
                  @JsonProperty("maxEat")double maxEat,
                  @JsonProperty("maxCount")int maxCount,
                  @JsonProperty(value = "edibleGene") HashMap<String, Double> edibleGene) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.standardWeight = standardWeight;
        this.maxEat = maxEat;
        this.edibleGene = edibleGene;
        this.maxCount = maxCount;
    }

    public void setEdibleGene(HashMap<String, Double> huntersGene) {
        this.edibleGene = edibleGene;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setStandardWeight(double standardWeight) {
        this.standardWeight = standardWeight;
    }

    public void setMaxEat(double maxEat) {
        this.maxEat = maxEat;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
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
        return edibleGene;
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
