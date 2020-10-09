package com.hippotech.controller;


import com.hippotech.controller.components.WeekTitle;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PrimaryViewController implements Initializable {
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
    GridPane timeLinePane;
    @FXML
    ScrollBar scBar;

    ArrayList<Double> highestHeightPerRow = new ArrayList<>();
    ArrayList<Double> heightListAllTable = new ArrayList<>();

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        tasks = taskService.getAllTask();
    }

    public void initTable() {
        int numCols = 9;
        int numRows = tasks.size();


        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(35);
            rowConstraints.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Task task = tasks.get(i);
                switch (j) {
                    case 0: {
                        ProjectName projectName = projectNameService.getProjectName(task.getPrName());
                        addPane(i, j, 20, task.getPrName(), projectName.getProjectColor());
                        break;
                    }
                    case 1: {
                        addPane(i, j, 20, task.getTitle(), "");
                        break;
                    }
                    case 2: {
                        Person person = personService.getPersonByName(task.getName());
                        addPane(i, j, 20, task.getName(), person.getColor());
                        break;
                    }
                    case 3: {
                        addPane(i, j, 20, task.getStartDate(), "");
                        break;
                    }
                    case 4: {
                        addPane(i, j, 20, task.getDeadline(), "");
                        break;
                    }
                    case 5: {
                        addPane(i, j, 20, task.getFinishDate(), "");
                        break;
                    }
                    case 6: {
                        addPane(i, j, 20, task.getExpectedTime() + "", "");
                        break;
                    }
                    case 7: {
                        addPane(i, j, 20, task.getFinishTime() + "", "");
                        break;
                    }
                    case 8: {
                        addPane(i, j, 20, task.getProcessed() + "%", "");
                        break;
                    }

                }

            }
        }
        for (int h = 0; h < numRows; h++) {
            double tempMaxHeight = 0;
            for (int j = 0; j < numCols; j++) {
                if (tempMaxHeight < heightListAllTable.get(h * numCols + j)) {
                    tempMaxHeight = heightListAllTable.get(h * numCols + j);
                }
            }
            highestHeightPerRow.add(tempMaxHeight);
        }
        System.out.println("size: " + highestHeightPerRow.size());
        for (Double i : highestHeightPerRow) {
            System.out.println("height: " + i);
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

    private void addTimeline() throws ParseException {
        timeLinePane.getColumnConstraints().clear();
        timeLinePane.getChildren().clear();
        LocalDate first = getMonday(LocalDate.of(2020, 1, 1));
        LocalDate last = getMonday(LocalDate.of(2020, 12, 31)).plusDays(5);
        int colsNum = new AddTaskViewController().workDays(first, last);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(35);
        for (int i = 0; i < colsNum; i++) {
            timeLinePane.getColumnConstraints().add(columnConstraints);
        }
        RowConstraints rowConstraints = new RowConstraints();

        for (Double height :
                highestHeightPerRow) {
            rowConstraints.setPrefHeight(height);
            timeLinePane.getRowConstraints().add(rowConstraints);
        }


        for (Task task : tasks) {
            int j = 0;
            for (LocalDate i = first; !i.isEqual(last); i = i.plusDays(1)) {
                if (i.getDayOfWeek().getValue() < 6) {

                    //TODO:
                    Pane cell = new Pane();
                    cell.setBackground(new Background(new BackgroundFill(Color.valueOf(getColor(i, task)),
                            CornerRadii.EMPTY, Insets.EMPTY)));
                    Label label = new Label(i.toString().substring(5));
                    cell.getChildren().add(label);
                    timeLinePane.add(cell, j, tasks.indexOf(task));
                    System.out.println(i);
                    j++;
                }
            }
        }
    }


    private void addPane(int colIndex, int rowIndex, int labelSize, String content, String colorCode) {
        Label label = new Label("  " + content);
        label.setWrapText(true);
        Text text = new Text("  " + content);
        text.setFont(new Font(15));
        text.setWrappingWidth(250);
        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        pane.setPrefWidth(10);
        pane.setMaxWidth(10);

        if (rowIndex == 0 || rowIndex == 2) {
            pane.setStyle("-fx-background-color: #" + colorCode.substring(2) + ";");
        }
        gridPane.add(pane, rowIndex, colIndex);
        ObservableList<Node> nodeList = pane.getChildren();
        for (Node i : nodeList) {
            heightListAllTable.add(i.getLayoutBounds().getHeight());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        try {
            addTimeline();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initTimelineTitle();
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

    public void initTimelineTitle() {
//        HBox pane = new HBox();
        HBox timeline = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
            WeekTitle weekTitle = new WeekTitle();
            LocalDate addDay = LocalDate.of(year, 1, 1).plusWeeks(i);
            weekTitle.setText(addDay);
            timeline.getChildren().add(weekTitle);
        }
        timeLineTitle.getChildren().add(timeline);
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
