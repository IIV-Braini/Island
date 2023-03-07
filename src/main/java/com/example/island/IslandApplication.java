package com.example.island;

import com.example.island.UI.IslandController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IslandApplication extends Application {

    private static TextArea console;
    private final static List<String> log = new ArrayList<>();
    private static int showTurn = 0;
    private static int countTurn = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IslandApplication.class.getResource("island-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Island");
        stage.setScene(scene);
        stage.show();
        IslandController controller = fxmlLoader.getController();
        console = controller.getConsole();
    }

    public static void showMessage(String s) {
        log.add(s);
        console.setText(s);
    }

    public static void showNextLog() {
        initialisationTurn();
        if(showTurn < countTurn) {
            console.setText(log.get(++showTurn));
        }
    }

    public static void showPrevLog() {
        initialisationTurn();
        if(showTurn > 0) {
            console.setText(log.get(--showTurn));
        }
    }

    public static void initialisationTurn() {
        if(countTurn == 0) {
            countTurn = log.size() - 1;
            showTurn = countTurn;
        }
    }

    public static void cleanLog() {
        log.clear();
        countTurn = 0;
        showTurn = 0;
    }

    public static void main(String[] args) {
        launch();
    }
}