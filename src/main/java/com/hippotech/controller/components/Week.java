package com.hippotech.controller.components;

import com.hippotech.model.Task;
import com.hippotech.service.TaskService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Week extends VBox {
    @FXML
    WeekTitle weekTitle;
    @FXML
    VBox weekTask;
    LocalDate date;
    ArrayList<Task> tasks;
    ArrayList<TaskRow> taskRows;
    TaskService service;

    public Week(int weekOfYear) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/hippotech/components/Week.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        date = LocalDate.parse("2020-10-01");
        service = new TaskService();
        tasks = service.getAllTask();
        taskRows = new ArrayList<>();
        for (Task task : tasks) {
            taskRows.add(new TaskRow(task, date));
        }
        weekTask.getChildren().addAll(taskRows);
        weekTitle.setText(LocalDate.of(2020, 10, 1));
    }
}
