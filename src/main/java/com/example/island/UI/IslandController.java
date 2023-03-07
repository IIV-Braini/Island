package com.example.island.UI;

import com.example.island.GameField.Field;
import com.example.island.GameField.Game;
import com.example.island.IslandApplication;
import com.example.island.subjects.Plant;
import com.example.island.subjects.herbivores.*;
import com.example.island.subjects.predators.*;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import java.util.HashMap;


public class IslandController {

    @FXML
    private TextArea console;
    @FXML
    private TextField millisecondTurn;
    @FXML
    private TextField width;
    @FXML
    private TextField height;
    @FXML
    private TextField wolfCount;
    @FXML
    private TextField foxCount;
    @FXML
    private TextField eagleCount;
    @FXML
    private TextField boaCount;
    @FXML
    private TextField bearCount;
    @FXML
    private TextField buffaloCount;
    @FXML
    private TextField caterpillarCount;
    @FXML
    private TextField deerCount;
    @FXML
    private TextField duckCount;
    @FXML
    private TextField goatCount;
    @FXML
    private TextField horseCount;
    @FXML
    private TextField mouseCount;
    @FXML
    private TextField sheepCount;
    @FXML
    private TextField wildBoarCount;
    @FXML
    private TextField rabbitCount;
    @FXML
    private TextField plantCount;
    @FXML
    private CheckBox wolfAdd;
    @FXML
    private CheckBox foxAdd;
    @FXML
    private CheckBox eagleAdd;
    @FXML
    private CheckBox boaAdd;
    @FXML
    private CheckBox bearAdd;
    @FXML
    private CheckBox buffaloAdd;
    @FXML
    private CheckBox caterpillarAdd;
    @FXML
    private CheckBox deerAdd;
    @FXML
    private CheckBox duckAdd;
    @FXML
    private CheckBox goatAdd;
    @FXML
    private CheckBox horseAdd;
    @FXML
    private CheckBox mouseAdd;
    @FXML
    private CheckBox rabbitAdd;
    @FXML
    private CheckBox sheepAdd;
    @FXML
    private CheckBox wildBoatAdd;
    @FXML
    private CheckBox plantAdd;
    @FXML
    private CheckBox endAfterAllAnimalDead;
    @FXML
    private CheckBox endAfterAllPredatorsDead;
    @FXML
    private CheckBox endAfterAllHerbivoreDead;
    @FXML
    private CheckBox endAfterCountTurn;
    @FXML
    private TextField countTurn;
    @FXML
    private TitledPane titledPane;

    @FXML
    protected void startButtonClick() {
        if (Game.isGameStopped()) {
            IslandApplication.cleanLog();
            Game.create(Integer.parseInt(millisecondTurn.getText()));
            Field.createField(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), initializationSubjects(), endAfterAllAnimalDead.isSelected(),  endAfterAllPredatorsDead.isSelected(), endAfterAllHerbivoreDead.isSelected(), endAfterCountTurn.isSelected(),  Integer.parseInt(countTurn.getText()));
            Game.startGame();
        }
        titledPane.setExpanded(false);
    }

    @FXML
    protected void stopButtonClick() {
        if(!Game.isGameStopped()) Game.stopGame();
        else console.setText("Игра уже окончена, закройте окно или начните новую!");
    }

    @FXML
    protected void prevButtonClick() {
        if(!Game.isGameStopped()) Game.stopGame();
        else IslandApplication.showPrevLog();
    }

    @FXML
    protected void newtButtonClick() {
        if(!Game.isGameStopped()) Game.stopGame();
        else IslandApplication.showNextLog();
    }

    private HashMap<Class<?>, Integer> initializationSubjects() {
        HashMap<Class<?>, Integer> map = new HashMap<>();
        if (plantAdd.isSelected()) map.put(Plant.class, Integer.valueOf(plantCount.getText()));
        if (wolfAdd.isSelected()) map.put(Wolf.class, Integer.valueOf(wolfCount.getText()));
        if (foxAdd.isSelected()) map.put(Fox.class, Integer.valueOf(foxCount.getText()));
        if (eagleAdd.isSelected()) map.put(Eagle.class, Integer.valueOf(eagleCount.getText()));
        if (boaAdd.isSelected()) map.put(Boa.class, Integer.valueOf(boaCount.getText()));
        if (bearAdd.isSelected()) map.put(Bear.class, Integer.valueOf(bearCount.getText()));
        if (buffaloAdd.isSelected()) map.put(Buffalo.class, Integer.valueOf(buffaloCount.getText()));
        if (caterpillarAdd.isSelected()) map.put(Caterpillar.class, Integer.valueOf(caterpillarCount.getText()));
        if (deerAdd.isSelected()) map.put(Deer.class, Integer.valueOf(deerCount.getText()));
        if (duckAdd.isSelected()) map.put(Duck.class, Integer.valueOf(duckCount.getText()));
        if (goatAdd.isSelected()) map.put(Goat.class, Integer.valueOf(goatCount.getText()));
        if (horseAdd.isSelected()) map.put(Horse.class, Integer.valueOf(horseCount.getText()));
        if (mouseAdd.isSelected()) map.put(Mouse.class, Integer.valueOf(mouseCount.getText()));
        if (rabbitAdd.isSelected()) map.put(Rabbit.class, Integer.valueOf(rabbitCount.getText()));
        if (sheepAdd.isSelected()) map.put(Sheep.class, Integer.valueOf(sheepCount.getText()));
        if (wildBoatAdd.isSelected()) map.put(WildBoar.class, Integer.valueOf(wildBoarCount.getText()));
        return map;
    }

    public TextArea getConsole() {
        return console;
    }

}