package com.hippotech.controller.components;

import com.hippotech.utilities.Constant;
import com.hippotech.utilities.DateAndColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
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

    public String getText() {
        return weekNum.getText();
    }

    public void setText(LocalDate date) {
        row.setText(DateAndColor.getMonday(date));
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        weekNum.setText(weekNumber + "");


        LocalDate dateNow = LocalDate.now();
        int weekNumberNow = dateNow.get(weekFields.weekOfWeekBasedYear());
        if (weekNumberNow == weekNumber && LocalDate.now().getYear() == (date.getYear())) {
            weekNum.setFill(Color.BLUE);
        }
    }


}
