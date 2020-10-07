package com.hippotech.controller;


import com.hippotech.model.ProjectName;
import com.hippotech.service.ProjectNameService;
import com.hippotech.utilities.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddProject {
    @FXML
    private TextField prName;
    @FXML
    private ColorPicker color;
    private ProjectNameService service = new ProjectNameService();

    public void okBtn(ActionEvent e) {
        ProjectName projectName = new ProjectName(prName.getText(), color.getValue().toString(), 0);
        service.addProjectName(projectName);
        _Alert.showInfoNotification(
                Constant.DialogConstant.SUCCESS_ADD_PROJECT
                );
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
