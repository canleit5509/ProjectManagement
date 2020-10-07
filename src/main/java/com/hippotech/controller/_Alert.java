package com.hippotech.controller;

import com.hippotech.utilities.Constant;
import javafx.scene.control.Alert;

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
}
