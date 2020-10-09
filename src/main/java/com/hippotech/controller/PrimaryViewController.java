package com.hippotech.controller;


import com.hippotech.controller.components.Week;
import com.hippotech.interfaces.IAddContentToWindow;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class PrimaryViewController implements Initializable {
    private final ObservableList<Task> listTask;
    @FXML
    Button btnEdit;
    @FXML
    Button btnAdd;
    @FXML
    Button btnDel;
    @FXML
    Button btnProject;
    @FXML
    Button btnPerson;

    @FXML
    TableView<Task> tbData;
    @FXML
    TableColumn<Task, String> tcProjectName;
    @FXML
    TableColumn<Task, String> tcTask;
    @FXML
    TableColumn<Task, String> tcNgPTr;
    @FXML
    TableColumn<Task, String> tcDateStart;
    @FXML
    TableColumn<Task, String> tcDeadline;
    @FXML
    TableColumn<Task, String> tcFinishDate;
    @FXML
    TableColumn<Task, String> tcExpectedTime;
    @FXML
    TableColumn<Task, String> tcFinishTime;
    @FXML
    TableColumn<Task, String> tcProcess;
    @FXML
    ScrollPane rightPane;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    @FXML
    private TableView<String> tbDetail;

    ModalWindowController modalWindowController = new ModalWindowController(this.getClass());

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        listTask = FXCollections.observableArrayList(taskService.getAllTask());
    }

    public void initTable() {
        tbData.setEditable(true);
        tbData.setMaxWidth(940);
        tcProjectName.setEditable(true);
        tcProjectName.setText(Constant.PrimaryConstant.PROJECT_NAME);
        tcProjectName.setCellValueFactory(new PropertyValueFactory<>("prName"));
        tcProjectName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcTask.setEditable(true);
        tcTask.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcTask.setCellFactory(TextFieldTableCell.forTableColumn());
        tcNgPTr.setEditable(true);
        tcNgPTr.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcDateStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tcDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        tcFinishDate.setCellValueFactory(new PropertyValueFactory<>("finishDate"));
        tcExpectedTime.setCellValueFactory(new PropertyValueFactory<>("expectedTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        tcProcess.setEditable(true);
        tcProcess.setCellValueFactory(new PropertyValueFactory<>("processed"));
        tbData.setItems(listTask);
    }

    public void refreshTable() {
        ObservableList<Task> taskList = FXCollections.observableArrayList(taskService.getAllTask());
        tbData.setItems(taskList);
        tcProjectName.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Task, String> call(TableColumn<Task, String> taskStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            ProjectName name = projectNameService.getProjectName(item);
                            this.setStyle("-fx-background-color: #" + name.getProjectColor().substring(2) + ";");
                            setText(item);
                        }
                    }
                };
            }
        });
        tcNgPTr.setCellFactory(new Callback<TableColumn<Task, String>, TableCell<Task, String>>() {
            @Override
            public TableCell<Task, String> call(TableColumn<Task, String> taskStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            Person person = personService.getPersonByName(item);
                            this.setStyle("-fx-background-color: #" + person.getColor().substring(2) + ";");
                            setText(item);
                        }
                    }
                };
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        refreshTable();

        HBox pane = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
//            WeekTitle weekTitle = new WeekTitle(LocalDate.of(year,1,1).plusWeeks(i));

            Week week = new Week(LocalDate.of(year, 1, 1).plusWeeks(i));
            pane.getChildren().add(week);
        }
        rightPane.setContent(pane);
        try {
            eventHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void eventHandler() {
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = (Node) mouseEvent.getSource();
                modalWindowController.showWindowModal(node,
                        "/com/hippotech/AddTaskView.fxml",
                        Constant.WindowTitleConstant.ADD_TASK_TITLE);
                refreshTable();
            }
        });
        btnDel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Task selected = tbData.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_DELETE);
                } else {
                    Optional<ButtonType> option = _Alert.showWaitConfirmation(
                            Constant.WindowTitleConstant.DELETE_TASK_TITLE,
                            Constant.DialogConstant.CONFIRM_DELETE_TASK
                    );
                    if (option.get() == ButtonType.OK) {
                        tbData.getItems().remove(selected);
                        taskService.deleteTask(selected);
                        _Alert.showInfoNotification(Constant.WindowTitleConstant.DELETE_TASK_TITLE);
                        refreshTable();
                    }
                }
            }
        });
        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Task selected = tbData.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_UPDATE);
                }else {
                    // TODO : Add controller in a function
                FXMLLoader loader = modalWindowController.getLoader("/com/hippotech/UpdateTaskView.fxml");
                Parent parent = modalWindowController.load(loader);
                Node node = (Node) mouseEvent.getSource();

                UpdateTaskViewController controller = loader.getController();
                controller.setTask(selected);
                controller.setComboBox();

                modalWindowController.showWindowModal(
                        node,
                        parent,
                        Constant.WindowTitleConstant.UPDATE_TASK_TITLE
                        );
                    refreshTable();
                }
            }
        });

        btnPerson.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                modalWindowController.showWindowModal(mouseEvent,
                        "/com/hippotech/PersonManagement.fxml",
                        Constant.WindowTitleConstant.PERSON_MANAGEMENT_TITLE);
                refreshTable();
            }
        });

        btnProject.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                modalWindowController.showWindowModal(e,
                        "/com/hippotech/ProjectManagement.fxml",
                        Constant.WindowTitleConstant.PROJECT_MANAGEMENT_TITLE);
                refreshTable();
            }
        });
    }
}
