package com.hippotech.controller.components;

import com.hippotech.utilities.Resizable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class TableTitle extends HBox {
    @FXML
    Rectangle recProject;
    @FXML
    Rectangle recTask;
    @FXML
    Rectangle recEmployee;
    @FXML
    Rectangle recStart;
    @FXML
    Rectangle recDeadline;
    @FXML
    Rectangle recFinish;
    @FXML
    Rectangle recExpected;
    @FXML
    Rectangle recFinishTime;
    @FXML
    Rectangle recProcess;

    public TableTitle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/components/TableTitle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Resizable.makeResizable(recProject);
        Resizable.makeResizable(recTask);
        Resizable.makeResizable(recEmployee);
        Resizable.makeResizable(recStart);
        Resizable.makeResizable(recDeadline);
        Resizable.makeResizable(recFinish);
        Resizable.makeResizable(recFinishTime);
        Resizable.makeResizable(recExpected);
        Resizable.makeResizable(recProcess);
    }
}
