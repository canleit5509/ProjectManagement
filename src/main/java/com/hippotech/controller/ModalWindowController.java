package com.hippotech.controller;

import com.hippotech.interfaces.IAddContentToWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Callable;

public class ModalWindowController{
    Class _class;

    public ModalWindowController(Class<?> aClass) {
        this._class = aClass;
    }
    public void showWindowModal(MouseEvent e, String viewSource, String title) {
        Node node = ((Node) e.getSource());
        showWindowModal(node, viewSource, title);
    }
    public void showWindowModal(ActionEvent e, String viewSource, String title) {
        Node node = ((Node) e.getSource());
        showWindowModal(node, viewSource, title);
    }
    //
    // TODO : Add controller in a function
    public void showWindowModal(Node node, String viewSource, String title) {
        FXMLLoader loader = getLoader(viewSource);
        Parent parent = load(loader);
//        addContentToWindow(parent);
        showWindowModal(node, parent, title);
    }

    public void showWindowModal(Node node, Parent parent, String title) {
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(parent);
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
        System.out.println(loader);

        return loader;
    }

    public Parent load(FXMLLoader loader) {
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

}
