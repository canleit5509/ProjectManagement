package com.hippotech.controller.components;

import com.hippotech.model.Task;
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
        System.out.println(this.task.toString());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/components/TaskRow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(this.task.getFinishDate());
        setFill(date);
    }

    public void setFill(LocalDate date) {
        mon.setFill(Paint.valueOf(getColor(date)));
        tue.setFill(Paint.valueOf(getColor(date.plusDays(1))));
        wed.setFill(Paint.valueOf(getColor(date.plusDays(2))));
        thu.setFill(Paint.valueOf(getColor(date.plusDays(3))));
        fri.setFill(Paint.valueOf(getColor(date.plusDays(4))));
    }

    public String getColor(LocalDate date) {
        LocalDate startDate = LocalDate.parse(this.task.getStartDate());
        if (date.isBefore(startDate)) return "#ffffff";

        LocalDate deadLine = LocalDate.parse(this.task.getDeadline());

        if (this.task.getFinishDate().length()>0) {
            LocalDate finishDate = LocalDate.parse(this.task.getFinishDate());
            if (finishDate.isBefore(deadLine)) {
                if (date.isBefore(finishDate)) return "#66d94c"; //xanh la nhat
                if (date.isEqual(finishDate)) return "#4fa83b"; // xanh dam
                if (date.isEqual(deadLine)) return "#d62a1e";   // do
            } else {
                if (date.isBefore(deadLine)) return "#66d94c"; //xanh la nhat
                if (date.isEqual(deadLine)) return "#d62a1e";   // do
                if (date.isBefore(finishDate)) return "#f2aa61";//cam
                if (date.isEqual(finishDate)) return "#f5f25b"; //vang
            }
        } else {
            LocalDate now = LocalDate.now();
            if (now.isBefore(deadLine)) {
                if (date.isBefore(now))return "#66d94c";
                if (date.isBefore(deadLine))return "#ffffff";
                if (date.isEqual(deadLine)) return "#d62a1e";
            } else {
                if (date.isBefore(now))return "#66d94c";
                if (date.isEqual(deadLine)) return "#d62a1e";
                if (date.isBefore(deadLine)) return "#f5f25b";
            }
        }
        return "#ffffff";
    }
}
