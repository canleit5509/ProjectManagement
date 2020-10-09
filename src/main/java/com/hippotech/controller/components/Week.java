package com.hippotech.controller.components;

import com.hippotech.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Week extends VBox {
    @FXML
    VBox weekTask;
    ArrayList<TaskRow> taskRows;

    public Week(LocalDate date, ArrayList<Task> tasks) {
        LocalDate monday = getMonday(date);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/components/Week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        taskRows = new ArrayList<>();
        for (Task task : tasks) {
            taskRows.add(new TaskRow(task, monday));
        }
        weekTask.getChildren().addAll(taskRows);
    }

    private LocalDate getMonday(LocalDate date) {
        LocalDate monday;
        monday = date.minus(date.getDayOfWeek().getValue() - 1, ChronoUnit.DAYS);
        return monday;
    }
}
