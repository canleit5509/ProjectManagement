package com.hippotech.controller;


import com.hippotech.model.Person;
import com.hippotech.service.PersonService;
import com.hippotech.utilities.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePerson implements Initializable {
    @FXML
    private Label txtID;
    @FXML
    private TextField txtName;
    @FXML
    private ColorPicker color;
    @FXML
    private RadioButton radioNow;
    @FXML
    private RadioButton radioRetired;
    private PersonService service;

    public UpdatePerson() {
        service = new PersonService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        radioNow.setToggleGroup(group);
        radioRetired.setToggleGroup(group);
    }

    public void setPerson(Person person) {
        txtID.setText(person.getId());
        txtName.setText(person.getName());
        color.setValue(Color.valueOf(person.getColor()));
        if (person.getRetired() == 0) {
            radioRetired.setSelected(false);
            radioNow.setSelected(true);
        } else {
            radioNow.setSelected(false);
            radioRetired.setSelected(true);
        }
    }

    public void okBtn(ActionEvent e) {
        String id = txtID.getText();
        String name = txtName.getText();
        String txtColor = color.getValue().toString();
        int retired = 0;
        if (radioRetired.isSelected())
            retired = 1;

        Person person = new Person(id, name, txtColor, retired);
        service.updatePerson(person);
        _Alert.showInfoNotification(Constant.DialogConstant.SUCCESS_ADD_PERSON);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
