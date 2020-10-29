package com.hippotech.controller;


import com.hippotech.model.Person;
import com.hippotech.service.PersonService;
import com.hippotech.utilities.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
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
        long time = new Date().getTime();
        String id = Long.toString(time).substring(0, 9);
        txtID.setText(id + "");
        service = new PersonService();
    }

    public void okBtn(ActionEvent e) {
        Person person = new Person(txtID.getText(), psName.getText(), color.getValue().toString(), 0);
        service.addPerson(person);
        _Alert.showInfoNotification(
                Constant.DialogConstant.SUCCESS_ADD_PERSON
        );
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
