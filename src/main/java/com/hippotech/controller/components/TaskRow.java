package com.hippotech.controller.components;

import com.hippotech.model.Task;
import com.hippotech.utilities.Constant;
import com.hippotech.utilities.DateAndColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.LocalDate;

public class TaskRow extends HBox {
    @FXML
    Rectangle mon;
    @FXML
    Rectangle tue;
    @FXML
    Rectangle wed;
    @FXML
    Rectangle thu;
    @FXML
    Rectangle fri;
    Task task;

    public TaskRow(Task task, LocalDate date) {
        this.task = task;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.FXMLPage.TASK_ROW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setFill(date);
    }

    public void setFill(LocalDate date) {
        mon.setFill(Paint.valueOf(DateAndColor.getColor(date, task)));
        tue.setFill(Paint.valueOf(DateAndColor.getColor(date.plusDays(1), task)));
        wed.setFill(Paint.valueOf(DateAndColor.getColor(date.plusDays(2), task)));
        thu.setFill(Paint.valueOf(DateAndColor.getColor(date.plusDays(3), task)));
        fri.setFill(Paint.valueOf(DateAndColor.getColor(date.plusDays(4), task)));
    }


}
