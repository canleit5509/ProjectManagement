package com.hippotech.controller;


import com.hippotech.model.Person;
import com.hippotech.service.PersonService;
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

public class ManagePerson implements Initializable {
    @FXML
    private TableView tbData;
    @FXML
    private RadioButton checkNow;
    @FXML
    private RadioButton checkRetire;
    @FXML
    private RadioButton checkAll;
    @FXML
    private TableColumn<Person, String> tcID;
    @FXML
    private TableColumn<Person, String> tcName;
    @FXML
    private Button btnKick;
    private PersonService service;
    private final ModalWindowController modalWindowController = new ModalWindowController(
            this.getClass());

    public ManagePerson() {
        service = new PersonService();
    }

    public void btnAdd(ActionEvent e) throws IOException {
        FXMLLoader loader = modalWindowController.getLoader(
                "/com/hippotech/AddPerson.fxml");
        Parent addProject = modalWindowController.load(loader);

        AddPerson addPerson = loader.getController();
        addPerson.setID();

        Node node = (Node) e.getSource();
        modalWindowController.showWindowModal(node,
                addProject,
                Constant.WindowTitleConstant.ADD_PERSON_TITLE);
        RefreshTable(service.getRetiredPeople(0));
        refreshColor();
    }

    public void btnUpdate(ActionEvent e) throws IOException {
        Person person = (Person) tbData.getSelectionModel().getSelectedItem();
        if (person == null) {
            _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_PERSON);
        } else {
            FXMLLoader loader = modalWindowController.getLoader(
                    "/com/hippotech/UpdatePerson.fxml");
            Parent parent = modalWindowController.load(loader);

            UpdatePerson updatePerson = loader.getController();
            updatePerson.setPerson(person);

            Node node = (Node) e.getSource();
            modalWindowController.showWindowModal(node,
                    parent, Constant.WindowTitleConstant.UPDATE_PERSON_TITLE);

            RefreshTable(service.getRetiredPeople(0));
            refreshColor();
            checkNow.setSelected(true);
            btnKick.setVisible(true);
        }

    }

    public void btnKick(ActionEvent actionEvent) {
        Person person = (Person) tbData.getSelectionModel().getSelectedItem();
        if (person == null) {
            _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_PERSON);
        } else {
            person.setRetired(1);
            service.updatePerson(person);
            RefreshTable(service.getRetiredPeople(0));
            refreshColor();

        }
    }

    public void refreshColor() {
        tcID.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Person, String> call(TableColumn<Person, String> taskStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            Person person = service.getPersonByID(item);
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
        ToggleGroup group = new ToggleGroup();
        checkNow.setToggleGroup(group);
        checkRetire.setToggleGroup(group);
        checkAll.setToggleGroup(group);
        checkNow.setSelected(true);
        ObservableList<Person> personList = FXCollections.observableArrayList(service.getRetiredPeople(0));
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbData.setItems(personList);
        refreshColor();
    }

    public void RefreshTable(ArrayList<Person> list) {
        ObservableList<Person> personList = FXCollections.observableArrayList(list);
        tbData.setItems(personList);
    }

    public void checkNow(ActionEvent actionEvent) {
        RefreshTable(service.getRetiredPeople(0));
        btnKick.setVisible(true);
        refreshColor();
    }

    public void checkRetire(ActionEvent actionEvent) {
        RefreshTable(service.getRetiredPeople(1));
        btnKick.setVisible(false);
        refreshColor();
    }

    public void checkAll(ActionEvent actionEvent) {
        RefreshTable(service.getAllPeople());
        btnKick.setVisible(false);
        refreshColor();
    }
}
