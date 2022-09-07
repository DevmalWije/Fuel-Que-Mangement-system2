package com.example.Task_2_Task_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXapplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXapplication.class.getResource("fuelQueueView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 927, 568);
        stage.setTitle("Fuel Queue Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}