package com.hippotech.controller;


import com.hippotech.model.ProjectName;
import com.hippotech.service.ProjectNameService;
import com.hippotech.utilities.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageProject implements Initializable {
    ProjectNameService service;
    @FXML
    private TableView tbData;
    @FXML
    private RadioButton checkNow;
    @FXML
    private RadioButton checkDone;
    @FXML
    private RadioButton checkAll;
    @FXML
    private TableColumn<ProjectName, String> tcName;
    @FXML
    private Button btnDone;
    ModalWindowController modalWindowController = new ModalWindowController(this.getClass());

    public ManageProject() {
        service = new ProjectNameService();
    }

    public void btnAdd(ActionEvent e) throws IOException {
        modalWindowController.showWindowModal(e,
                Constant.FXMLPage.ADD_PROJECT,
                Constant.WindowTitleConstant.ADD_PROJECT_TITLE);
        RefreshTable(service.getAllDoneProject(0));
        refreshColor();
        checkNow.setSelected(true);
    }

    public void btnUpdate(ActionEvent e) throws IOException {
        ProjectName projectName = (ProjectName) tbData.getSelectionModel().getSelectedItem();
        if (projectName == null) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_A_PROJECT);
        } else {
            FXMLLoader loader = modalWindowController.getLoader(Constant.FXMLPage.UPDATE_PROJECT);
            Parent parent = modalWindowController.load(loader);

            UpdateProject updateProject1 = loader.getController();
            updateProject1.setProject(projectName);
            updateProject1.oldName = projectName.getProjectName();
            Node node = (Node) e.getSource();
            modalWindowController.showWindowModal(node, parent, Constant.WindowTitleConstant.UPDATE_PERSON_TITLE);

            RefreshTable(service.getAllDoneProject(0));
            refreshColor();
            checkNow.setSelected(true);
            btnDone.setVisible(true);
        }
    }

    public void btnKick(ActionEvent actionEvent) {
        ProjectName projectName = (ProjectName) tbData.getSelectionModel().getSelectedItem();
        if (projectName == null) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_A_PROJECT);
        } else {
            projectName.setDone(1);
            service.updateProject(projectName, projectName.getProjectName());
            RefreshTable(service.getAllDoneProject(0));
            refreshColor();
        }
    }

    public void refreshColor() {
        tcName.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProjectName, String> call(TableColumn<ProjectName, String> taskStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            ProjectName projectName = service.getProjectName(item);
                            this.setStyle("-fx-background-color: #" + projectName.getProjectColor().substring(2) + ";");
                            setText(item);
                        }
                    }
                };
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        checkNow.setToggleGroup(group);
        checkDone.setToggleGroup(group);
        checkAll.setToggleGroup(group);
        checkNow.setSelected(true);
        ObservableList<ProjectName> personList = FXCollections.observableArrayList(service.getAllDoneProject(0));
        tcName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        tbData.setItems(personList);
        refreshColor();
    }

    public void RefreshTable(ArrayList<ProjectName> list) {
        ObservableList<ProjectName> projectList = FXCollections.observableArrayList(list);
        tbData.setItems(projectList);
    }

    public void checkNow(ActionEvent actionEvent) {
        RefreshTable(service.getAllDoneProject(0));
        btnDone.setVisible(true);
        refreshColor();
    }

    public void checkRetire(ActionEvent actionEvent) {
        RefreshTable(service.getAllDoneProject(1));
        btnDone.setVisible(false);
        refreshColor();
    }

    public void checkAll(ActionEvent actionEvent) {
        RefreshTable(service.getAllProject());
        btnDone.setVisible(false);
        refreshColor();
    }
}
