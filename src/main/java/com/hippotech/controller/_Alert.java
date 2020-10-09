package com.hippotech.controller;

import com.hippotech.utilities.Constant;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class _Alert {
    public static void showInfoNotification(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constant.DialogConstant.NOTIFICATION_TITLE);
        alert.setHeaderText(content);
        alert.show();
    }

    public static void showWaitInfoNotification(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constant.DialogConstant.NOTIFICATION_TITLE);
        alert.setHeaderText(content);
        alert.showAndWait();
    }
    public static void showWaitInfoWarning(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Constant.DialogConstant.WARNING_TITLE);
        alert.setHeaderText(content);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showWaitConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(content);
        Optional<ButtonType> option = alert.showAndWait();
        return option;
    }

}
