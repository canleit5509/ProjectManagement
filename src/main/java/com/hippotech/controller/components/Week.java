package com.hippotech.controller.components;

import com.hippotech.model.Task;
import com.hippotech.service.TaskService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Week extends VBox {
    @FXML
    WeekTitle weekTitle;
    @FXML
    VBox weekTask;
    ArrayList<Task> tasks;
    ArrayList<TaskRow> taskRows;
    TaskService service;

    public Week(LocalDate date) {
        LocalDate monday = getMonday(date);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/components/Week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        service = new TaskService();
        tasks = service.getAllTask();
//        taskRows = new ArrayList<>();
//        for (Task task : tasks) {
//            taskRows.add(new TaskRow(task, monday));
//        }
//        weekTask.getChildren().addAll(taskRows);
//        weekTitle.setText(monday);


    }

    private LocalDate getMonday(LocalDate date) {
        LocalDate monday;
        monday = date.minus(date.getDayOfWeek().getValue() - 1, ChronoUnit.DAYS);
        return monday;
    }
}
