package com.hippotech.controller;

import com.hippotech.service.TaskService;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CustomViewController extends HBox {
    TaskService taskService;
    @FXML
    private TextField textField;

    public CustomViewController() {
        taskService = new TaskService();
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("/com/hippotech/fxml/customview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

}
