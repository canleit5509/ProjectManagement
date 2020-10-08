package com.hippotech.controller;


import com.hippotech.controller.components.TableTitle;
import com.hippotech.controller.components.Week;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PrimaryViewController implements Initializable {
    private final ObservableList<Task> listTask;
    @FXML
    Button btnEdit;
    @FXML
    Button btnAdd;
    @FXML
    Button btnDel;
    //    @FXML
//    TableView<Task> tbData;
//    @FXML
//    TableColumn<Task, String> tcProjectName;
//    @FXML
//    TableColumn<Task, String> tcTask;
//    @FXML
//    TableColumn<Task, String> tcNgPTr;
//    @FXML
//    TableColumn<Task, String> tcDateStart;
//    @FXML
//    TableColumn<Task, String> tcDeadline;
//    @FXML
//    TableColumn<Task, String> tcFinishDate;
//    @FXML
//    TableColumn<Task, String> tcExpectedTime;
//    @FXML
//    TableColumn<Task, String> tcFinishTime;
//    @FXML
//    TableColumn<Task, String> tcProcess;
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

//    public void initTable() {
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
//    }
//
//    public void refreshTable() {
//        ObservableList<Task> taskList = FXCollections.observableArrayList(taskService.getAllTask());
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
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        initTable();
//        refreshTable();

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
//    }

}
