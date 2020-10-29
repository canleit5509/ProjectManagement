package com.hippotech.controller.components;

import com.hippotech.utilities.Constant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

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
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource(Constant.FXMLPage.DAY_ROW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public boolean isDateInWeek(LocalDate date, LocalDate dateNow){
        for(int i=0;i<=4;i++){
            if(dateNow.isEqual(date.plus(i, ChronoUnit.DAYS))){
                return true;
            }
        }
        return false;
    }

    public void setText(LocalDate date) {
        mon.textProperty().set(getDayOfMonth(date));
        tue.textProperty().setValue(getDayOfMonth(date.plus(1, ChronoUnit.DAYS)));
        wed.textProperty().setValue(getDayOfMonth(date.plus(2, ChronoUnit.DAYS)));
        thu.textProperty().setValue(getDayOfMonth(date.plus(3, ChronoUnit.DAYS)));
        fri.textProperty().setValue(getDayOfMonth(date.plus(4, ChronoUnit.DAYS)));

        Date dateTemp = new Date();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = dateTemp.getMonth() + 1;
        int year = dateTemp.getYear() + 1900;
        LocalDate dateNow = LocalDate.of(year, month, day);
        if (isDateInWeek(date, dateNow)) {
            mon.setFill(Color.BLUE);
            tue.setFill(Color.BLUE);
            wed.setFill(Color.BLUE);
            thu.setFill(Color.BLUE);
            fri.setFill(Color.BLUE);
        }
    }
    public String getText(){
        return mon.getText();
    }
    public String getDayOfMonth(LocalDate date) {
        return date.getDayOfMonth() + "/" + date.getMonthValue();
    }
}
