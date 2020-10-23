package com.hippotech.controller.components;

import com.hippotech.utilities.Resizable;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    ArrayList<DoubleProperty> widthList;
    ArrayList<Rectangle> rectangles;

    public TableTitle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/components/TableTitle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rectangles = new ArrayList<>();
        widthList = new ArrayList<>();
        rectangles.addAll(Arrays.asList(recProject, recTask, recEmployee, recStart, recDeadline,
                recFinish, recExpected, recFinishTime, recProcess));
        for (Rectangle rec : rectangles) {
            Resizable.makeResizable(rec);
            widthList.add(rec.widthProperty());
        }
    }
}
