package com.hippotech.controller.components;

import com.hippotech.utilities.Constant;
import com.hippotech.utilities.DateAndColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeekTitle extends VBox {
    @FXML
    Text weekNum;
    @FXML
    DayRow row;

    public WeekTitle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.FXMLPage.WEEK_TITLE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setText(LocalDate date) {
        row.setText(DateAndColor.getMonday(date));
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        weekNum.setText(weekNumber + "");

        Date dateTemp = new Date();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = dateTemp.getMonth() + 1;
        int year = dateTemp.getYear() + 1900;
        LocalDate dateNow = LocalDate.of(year, month, day);
        int weekNumberNow = dateNow.get(weekFields.weekOfWeekBasedYear());
        if(weekNumberNow==weekNumber && year==(date.getYear())){
            weekNum.setFill(Color.BLUE);
            weekNum.setFont(new Font(18));
        }
    }

    public String getText() {
        return weekNum.getText();
    }


}
