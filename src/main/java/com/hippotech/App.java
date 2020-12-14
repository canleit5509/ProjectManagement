package com.hippotech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/hippotech/PrimaryView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Project Manage");
        stage.setMaximized(true);
        stage.setResizable(true);

        stage.setScene(scene);
        stage.show();
    }
}