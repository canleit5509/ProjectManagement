package com.hippotech;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChildPane extends StackPane implements Initializable{
    @FXML
    Text text;
    public ChildPane(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/child.fxml"));
        System.out.println(getClass().getResource("/com/hippotech/child.fxml"));
        fxmlLoader.setRoot(ChildPane.this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return text.textProperty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}