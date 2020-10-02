package com.hippotech.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DayRow extends HBox {
    @FXML
    Text mon;
    @FXML
    Text tue;
    @FXML
    Text wed;
    @FXML
    Text thu;
    @FXML
    Text fri;

    public DayRow() {
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("/com/hippotech/components/DayRow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public void setText(LocalDate date) {
        mon.textProperty().set(getDayOfMonth(date));
        tue.textProperty().setValue(getDayOfMonth(date.plus(1, ChronoUnit.DAYS)));
        wed.textProperty().setValue(getDayOfMonth(date.plus(2, ChronoUnit.DAYS)));
        thu.textProperty().setValue(getDayOfMonth(date.plus(3, ChronoUnit.DAYS)));
        fri.textProperty().setValue(getDayOfMonth(date.plus(4, ChronoUnit.DAYS)));
    }
    public String getText(){
        return mon.getText();
    }
    public String getDayOfMonth(LocalDate date) {
        return date.getDayOfMonth() + "/" + date.getMonthValue();
    }
}
