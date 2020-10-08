package com.hippotech.controller;


import com.hippotech.model.Person;
import com.hippotech.service.PersonService;
import com.hippotech.utilities.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPerson implements Initializable {
    PersonService service;
    @FXML
    private Label txtID;
    @FXML
    private TextField psName;
    @FXML
    private ColorPicker color;

    public void setID() {
        Random random = new Random();
        long id = new Date().getTime();
//        int id = random.nextInt(899999) + 100000;
        txtID.setText(id + "");
        service = new PersonService();
    }

    public void okBtn(ActionEvent e) {
        Person person = new Person(txtID.getText(), psName.getText(), color.getValue().toString(), 0);
        service.addPerson(person);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constant.DialogConstant.NOTIFICATION_TITLE);
        alert.setHeaderText(Constant.DialogConstant.SUCCESS_ADD_PERSON);
        alert.show();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setID();
    }
}
