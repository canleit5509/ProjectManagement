package com.hippotech;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChildPane extends StackPane{

    public ChildPane(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("/com/hippotech/child.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(new ChildController());
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
//            throw new RuntimeException(exception);
        }

    }

    class ChildController implements Initializable {
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
    }

}