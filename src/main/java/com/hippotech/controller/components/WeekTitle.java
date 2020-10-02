package com.hippotech.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekTitle extends VBox {
    @FXML
    Text weekNum;
    @FXML
    DayRow row;

    public WeekTitle() {
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("/com/hippotech/components/WeekTitle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }
    public void setText(LocalDate date) {
        row.setText(date);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        weekNum.setText(weekNumber + "");
    }
    public String getText(){
        return weekNum.getText();
    }
}
