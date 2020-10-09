package com.hippotech.controller;


import com.hippotech.controller.components.Week;
import com.hippotech.controller.components.WeekTitle;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PrimaryViewController implements Initializable {
    private final ObservableList<Task> listTask;
    @FXML
    GridPane gridPane;
    @FXML
    Button btnEdit;
    @FXML
    Button btnAdd;
    @FXML
    Button btnDel;
    @FXML
    VBox rightPane;
    @FXML
    VBox timeLineTitle;
    @FXML
    HBox titleBox;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    ArrayList<Task> tasks;
    @FXML
    GridPane rowT;

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        listTask = FXCollections.observableArrayList(taskService.getAllTask());
        tasks = taskService.getAllTask();
    }

    public void initTable() {
        ArrayList<Task> listTask2 = taskService.getAllTask();
        int numCols = 9;
        int numRows = listTask.size();
//        for (int i = 0; i < numCols; i++) {
//            ColumnConstraints colConstraints = new ColumnConstraints(200);
//            colConstraints.setHgrow(Priority.ALWAYS);
//            Pane pane = new Pane();
//            Rectangle rectangle = new Rectangle();
//            gridPane.getColumnConstraints().add(colConstraints);
//        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints(35);
            rowConstraints.setMaxHeight(50);
            rowConstraints.setMinHeight(100);
            rowConstraints.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConstraints);
        }

//        for (int i = 0; i < num; i++) {
//            for (int j = 0; j < numRows; j++) {
//                TaskDTO taskDTO = listTask.get(j);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Task task = listTask2.get(i);
//                addPane(i, j , i*10 + j + 10, i*numCols+j+"");
//                addPane(i, j , 50, i*numCols+j+"");
                switch (j) {
//                    case 0: {
//                        addPane(i, j, 50, taskDTO.getId());
//                        break;
//                    }
                    case 0: {
                        addPane(i, j, 20, task.getPrName());
                        break;
                    }
                    case 1: {
                        addPane(i, j, 20, task.getTitle());
                        break;
                    }
                    case 2: {
                        addPane(i, j, 20, task.getName());
                        break;
                    }
                    case 3: {
                        addPane(i, j, 20, task.getStartDate());
                        break;
                    }
                    case 4: {
                        addPane(i, j, 20, task.getDeadline());
                        break;
                    }
                    case 5: {
                        addPane(i, j, 20, task.getFinishDate());
                        break;
                    }
                    case 6: {
                        addPane(i, j, 20, task.getExpectedTime() + "");
                        break;
                    }
                    case 7: {
                        addPane(i, j, 20, task.getFinishTime() + "");
                        break;
                    }
                    case 8: {
                        addPane(i, j, 20, task.getProcessed() + "%");
                        break;
                    }

                }

            }
        }
    }


    public String getColor(LocalDate date, Task task) {
        LocalDate startDate = LocalDate.parse(task.getStartDate());
        if (date.isBefore(startDate)) return Constant.COLOR.WHITE;

        LocalDate deadLine = LocalDate.parse(task.getDeadline());
        if (task.getFinishDate() != null) {
            LocalDate finishDate = LocalDate.parse(task.getFinishDate());
            if (finishDate.isBefore(deadLine)) {
                if (date.isEqual(finishDate)) return Constant.COLOR.DARK_GREEN;
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(finishDate) || date.isEqual(startDate))
                    return Constant.COLOR.SOFT_GREEN;
            } else {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isEqual(finishDate)) return Constant.COLOR.YELLOW;
                if (finishDate.isEqual(deadLine) && finishDate.isEqual(date)) return Constant.COLOR.DARK_GREEN;
                if (date.isBefore(deadLine) || date.isEqual(startDate)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(finishDate)) return Constant.COLOR.ORANGE;

            }
        } else {
            LocalDate now = LocalDate.now();
            if (now.isBefore(deadLine)) {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(now) || date.isEqual(startDate)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.WHITE;

            } else {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(now) || date.isEqual(startDate)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.YELLOW;
            }
        }
        return Constant.COLOR.WHITE;
    }

    private LocalDate getMonday(LocalDate date) {
        LocalDate monday;
        monday = date.minus(date.getDayOfWeek().getValue() - 1, ChronoUnit.DAYS);
        return monday;
    }

    private void addTimelineRow(Task task) {
        rowT.getColumnConstraints().clear();
        rowT.getChildren().clear();
        int colsNum = 52 * 5;
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(35);
        for (int i = 0; i < colsNum; i++) {
            rowT.getColumnConstraints().add(columnConstraints);
        }

        //cell.setBackground(new Background(new BackgroundFill(Color.valueOf())));
        LocalDate first = getMonday(LocalDate.of(2020, 1, 1));
        LocalDate last = getMonday(LocalDate.of(2020, 11, 1));
        int j = 0;
        for (LocalDate i = first; !i.isEqual(last); i = i.plusDays(1)) {
            if (i.getDayOfWeek().getValue() < 6) {
                Pane cell = new Pane();
                cell.setBackground(new Background(new BackgroundFill(Color.valueOf(getColor(i, task)),
                        CornerRadii.EMPTY, Insets.EMPTY)));
                Label label = new Label(i.toString());
                cell.getChildren().add(label);
                rowT.add(cell, j, 0);
                System.out.println(i);
                j++;
            }
        }
    }

    private void addPane(int colIndex, int rowIndex, int labelSize, String content) {

        int cellNum = rowIndex * 2 + colIndex + 1;
        Label label = new Label("  " + content);
        TextField tf = new TextField();
        tf.setLayoutX(10);
        tf.setLayoutY(20);
        Pane pane = new Pane();
        pane.getChildren().add(label);
        gridPane.add(pane, rowIndex, colIndex);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        addTimelineRow(tasks.get(1));
        //refreshTimeline();
        initScrollBar();
    }

    public void initScrollBar() {
//        scBar.setLayoutX(titleBox.getHeight() + rightPane.getHeight() - scBar.getHeight());
//        scBar.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                System.out.println(t1.doubleValue() + "V");
//                rightPane.setLayoutY(-t1.doubleValue());
//            }
//        });
    }

    public void refreshTimeline() {
        HBox pane = new HBox();
        HBox timeline = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
            WeekTitle weekTitle = new WeekTitle();
            LocalDate addDay = LocalDate.of(year, 1, 1).plusWeeks(i);
            weekTitle.setText(addDay);
            timeline.getChildren().add(weekTitle);
            Week week = new Week(addDay, tasks);
            pane.getChildren().add(week);
        }
        timeLineTitle.getChildren().add(timeline);
        rightPane.getChildren().add(pane);


//        try {
//            eventHandler();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            eventHandler();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    private void eventHandler() {
//        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//                FXMLLoader loader = getLoader("/com/hippotech/AddTaskView.fxml");
//                Parent addTaskParent = null;
//                try {
//                    addTaskParent = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Scene scene = new Scene(addTaskParent);
//                Stage addTaskWindow = new Stage();
//                addTaskWindow.setTitle("Thêm công việc");
//                addTaskWindow.setScene(scene);
//                addTaskWindow.initModality(Modality.WINDOW_MODAL);
//                addTaskWindow.initOwner(stage);
//                addTaskWindow.showAndWait();
//                refreshTable();
//                refreshTimeline();
//            }
//        });
//        btnDel.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//                FXMLLoader loader = getLoader("/com/hippotech/UpdateTaskView.fxml");
//                Parent updateTaskParent = null;
//                try {
//                    updateTaskParent = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Scene scene = new Scene(updateTaskParent);
//                UpdateTaskViewController controller = loader.getController();
//                Task selected = tbData.getSelectionModel().getSelectedItem();
//                if (selected == null) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Warning");
//                    alert.setHeaderText("Vui lòng chọn công việc cần chỉnh sửa");
//                    alert.show();
//                } else {
//                    controller.setTask(selected);
//                    controller.setComboBox();
//                    Stage updateTaskWindow = new Stage();
//                    updateTaskWindow.setTitle("Chỉnh sửa công việc");
//                    updateTaskWindow.setScene(scene);
//                    updateTaskWindow.initModality(Modality.WINDOW_MODAL);
//                    updateTaskWindow.initOwner(stage);
//                    updateTaskWindow.showAndWait();
//                    refreshTable();
//                    refreshTimeline();
//                }
//            }
//        });
//        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/com/hippotech/UpdateTaskView.fxml"));
//                Parent updateTaskParent = null;
//                try {
//                    updateTaskParent = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Scene scene = new Scene(updateTaskParent);
//                UpdateTaskViewController controller = loader.getController();
//                Task selected = tbData.getSelectionModel().getSelectedItem();
//                if (selected == null) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Warning");
//                    alert.setHeaderText("Vui lòng chọn công việc cần chỉnh sửa");
//                    alert.show();
//                } else {
//                    controller.setTask(selected);
//                    controller.setComboBox();
//                    Stage updateTaskWindow = new Stage();
//                    updateTaskWindow.setTitle("Chỉnh sửa công việc");
//                    updateTaskWindow.setScene(scene);
//                    updateTaskWindow.initModality(Modality.WINDOW_MODAL);
//                    updateTaskWindow.initOwner(stage);
//                    updateTaskWindow.showAndWait();
//                    refreshTable();
//                    refreshTimeline();
//                }
//            }
//        });
//    }
//
//    public FXMLLoader getLoader(String source) {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource(source));
//        return loader;
//    }
//
//
//    public void Delete(ActionEvent e) {
//        Task selected = tbData.getSelectionModel().getSelectedItem();
//        if (selected == null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Warning");
//            alert.setHeaderText("Vui lòng chọn công việc cần xóa");
//            alert.show();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Xóa công việc");
//            alert.setHeaderText("Bạn có chắc chắn muốn xóa công việc này?");
//            Optional<ButtonType> option = alert.showAndWait();
//            if (option.get() == null) {
//            } else if (option.get() == ButtonType.OK) {
//                tbData.getItems().remove(selected);
//                taskService.deleteTask(selected);
//                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
//                alert2.setTitle("Thông báo");
//                alert2.setHeaderText("Đã xóa");
//                alert2.show();
//                refreshTable();
//                refreshTimeline();
//            } else if (option.get() == ButtonType.CANCEL) {
//            }
//        }
//    }
//
//    public void btnProject(ActionEvent e) throws IOException {
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/com/hippotech/ProjectManagement.fxml"));
//        Parent addTaskParent = loader.load();
//        Scene scene = new Scene(addTaskParent);
//        Stage addTaskWindow = new Stage();
//        addTaskWindow.setTitle("Quản lý nhân sự");
//        addTaskWindow.setScene(scene);
//        addTaskWindow.initModality(Modality.WINDOW_MODAL);
//        addTaskWindow.initOwner(stage);
//        addTaskWindow.showAndWait();
//        refreshTable();
//        refreshTimeline();
//    }
//
//    public void btnPerson(ActionEvent e) throws IOException {
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/com/hipptech/PersonManagement.fxml"));
//        Parent addTaskParent = loader.load();
//        Scene scene = new Scene(addTaskParent);
//        Stage addTaskWindow = new Stage();
//        addTaskWindow.setTitle("Quản lý nhân sự");
//        addTaskWindow.setScene(scene);
//        addTaskWindow.initModality(Modality.WINDOW_MODAL);
//        addTaskWindow.initOwner(stage);
//        addTaskWindow.showAndWait();
//        refreshTable();
//        refreshTimeline();
//    }

}
