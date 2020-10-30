package com.hippotech.utilities;

import com.hippotech.model.Task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateAndColor {

    public static LocalDate getMonday(LocalDate date) {
        LocalDate monday;
        monday = date.minus(date.getDayOfWeek().getValue() - 1, ChronoUnit.DAYS);
        return monday;
    }

    public static String getColor(LocalDate date, Task task) {
        LocalDate startDate = LocalDate.parse(task.getStartDate());
        if (date.isBefore(startDate)) return Constant.Color.WHITE;

        LocalDate deadLine = LocalDate.parse(task.getDeadline());
        if (task.getFinishDate() != null) {
//            System.out.println("22: " + task.getFinishDate());
//            if(task.getFinishDate().equals("")){
//                task.setFinishDate("null");
//            }
            LocalDate finishDate = LocalDate.parse(task.getFinishDate());
            if (finishDate.isBefore(deadLine)) {
                if (date.isEqual(finishDate)) return Constant.Color.DARK_GREEN;
                if (date.isEqual(deadLine)) return Constant.Color.RED;
                if (date.isBefore(finishDate) || date.isEqual(startDate))
                    return Constant.Color.SOFT_GREEN;
            } else {
                if (finishDate.isEqual(deadLine) && finishDate.isEqual(date)) return Constant.Color.DARK_GREEN;
                if (date.isEqual(deadLine)) return Constant.Color.RED;
                if (date.isEqual(finishDate)) return Constant.Color.YELLOW;
                if (date.isBefore(deadLine) || date.isEqual(startDate)) return Constant.Color.SOFT_GREEN;
                if (date.isBefore(finishDate)) return Constant.Color.ORANGE;

            }
        } else {
            LocalDate now = LocalDate.now();
            if (now.isBefore(deadLine)) {
                if (date.isEqual(deadLine)) return Constant.Color.RED;
                if (date.isBefore(now) || date.isEqual(startDate)) return Constant.Color.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.Color.WHITE;

            } else {
                if (date.isEqual(deadLine)) return Constant.Color.RED;
                if (date.isEqual(startDate) || date.isBefore(deadLine)) return Constant.Color.SOFT_GREEN;
                if (date.isBefore(now) || date.equals(now)) return Constant.Color.ORANGE;
            }
        }
        return Constant.Color.WHITE;
    }
}
