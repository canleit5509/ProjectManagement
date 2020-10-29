package com.hippotech.controller.components;

import com.hippotech.utilities.Constant;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TableTitle extends HBox {
    public TableTitle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.FXMLPage.TABLE_TITLE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
