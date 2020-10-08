package com.hippotech.controller;


import com.hippotech.controller.components.TableTitle;
import com.hippotech.controller.components.Week;
import com.hippotech.dto.TaskDTO;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
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
    ScrollPane rightPane, leftPane;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    ArrayList<Task> tasks;
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
//        addResizeListeners();
//        makeResizable(50);
//        tbData.setEditable(true);
//        tbData.setMaxWidth(940);
//        tcProjectName.setEditable(true);
//        tcProjectName.setText(Constant.PrimaryConstant.PROJECT_NAME);
//        tcProjectName.setCellValueFactory(new PropertyValueFactory<>("prName"));
//        tcProjectName.setCellFactory(TextFieldTableCell.forTableColumn());
//        tcTask.setEditable(true);
//        tcTask.setCellValueFactory(new PropertyValueFactory<>("title"));
//        tcTask.setCellFactory(TextFieldTableCell.forTableColumn());
//        tcNgPTr.setEditable(true);
//        tcNgPTr.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tcDateStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        tcDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
//        tcFinishDate.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
//        tcExpectedTime.setCellValueFactory(new PropertyValueFactory<>("expectedTime"));
//        tcFinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
//        tcProcess.setEditable(true);
//        tcProcess.setCellValueFactory(new PropertyValueFactory<>("processed"));
//        tbData.setItems(listTask);
    }

    public void refreshTable() {
        ObservableList<Task> taskList = FXCollections.observableArrayList(taskService.getAllTask());
//        tbData.setItems(taskList);
//        tcProjectName.setCellFactory(new Callback<>() {
//            @Override
//            public TableCell<Task, String> call(TableColumn<Task, String> taskStringTableColumn) {
//                return new TableCell<>() {
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (!isEmpty()) {
//                            ProjectName name = projectNameService.getProjectName(item);
//                            this.setStyle("-fx-background-color: #" + name.getProjectColor().substring(2) + ";");
//                            setText(item);
//                        }
//                    }
//                };
//            }
//        });
//        tcNgPTr.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
//            @Override
//            public TableCell<Task, String> call(TableColumn<Task, String> taskStringTableColumn) {
//                return new TableCell<>() {
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (!isEmpty()) {
//                            Person person = personService.getPersonByName(item);
//                            this.setStyle("-fx-background-color: #" + person.getColor().substring(2) + ";");
//                            setText(item);
//                        }
//                    }
//                };
//            }
//        });
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
        refreshTable();
        refreshTimeline();
    }

    public void refreshTimeline() {
        HBox pane = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
//            WeekTitle weekTitle = new WeekTitle(LocalDate.of(year,1,1).plusWeeks(i));

            Week week = new Week(LocalDate.of(year, 1, 1).plusWeeks(i), tasks);
            pane.getChildren().add(week);
        }
        rightPane.setContent(pane);

        TableTitle tableTitle = new TableTitle();
        VBox left = new VBox();
        left.getChildren().add(tableTitle);
        leftPane.setContent(left);
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
