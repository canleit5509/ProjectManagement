package com.hippotech.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalWindowController {
    Class _class;

    public ModalWindowController(Class<?> aClass) {
        this._class = aClass;
    }

    public void showWindowModal(MouseEvent mouseEvent, String viewSource, String title) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = getLoader(viewSource);
        Parent view = null;
        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(view);
        Stage newWindow = new Stage();
        newWindow.setTitle(title);
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.showAndWait();
    }

    public FXMLLoader getLoader(String source) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(_class.getResource(source));
        return loader;
    }

}
