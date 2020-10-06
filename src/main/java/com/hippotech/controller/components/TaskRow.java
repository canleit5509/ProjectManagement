package com.hippotech.controller.components;

import com.hippotech.model.Task;
import com.hippotech.utilities.Constant;
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
        if (date.isBefore(startDate)) return Constant.COLOR.WHITE;

        LocalDate deadLine = LocalDate.parse(this.task.getDeadline());
        //TODO: Handle null finish date
        if (!this.task.getFinishDate().equals(null)) {
            LocalDate finishDate = LocalDate.parse(this.task.getFinishDate());
            if (finishDate.isBefore(deadLine)) {
                if (date.isEqual(finishDate)) return Constant.COLOR.DARK_GREEN; // xanh dam
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;   // do
                if (date.isBefore(finishDate)) return Constant.COLOR.SOFT_GREEN; //xanh la nhat
            } else {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;   // do
                if (date.isEqual(finishDate)) return Constant.COLOR.YELLOW; //vang
                if (finishDate.isEqual(deadLine) && finishDate.isEqual(date)) return Constant.COLOR.DARK_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.SOFT_GREEN; //xanh la nhat
                if (date.isBefore(finishDate)) return Constant.COLOR.ORANGE;//cam

            }
        } else {
            LocalDate now = LocalDate.now();
            if (now.isBefore(deadLine)) {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(now)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.WHITE;

            } else {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(now)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.YELLOW;
            }
        }
        return Constant.COLOR.WHITE;
    }
}
