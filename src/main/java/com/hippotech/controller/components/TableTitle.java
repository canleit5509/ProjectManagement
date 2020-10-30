package com.hippotech.controller.components;

import com.hippotech.utilities.Constant;
import com.hippotech.utilities.Resizable;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableTitle extends HBox {
    @FXML
    HBox box;
    ArrayList<String> titleList = new ArrayList<>(Arrays.asList("Project",
            "Task",
            "Employee",
            "Start",
            "Deadline",
            "Finish",
            "Expected",
            "Finish Time",
            "Process"));
    ArrayList<Double> prefWidthList = new ArrayList<>(Arrays.asList(
            100d,
            260d,
            90d,
            90d,
            90d,
            90d,
            85d,
            85d,
            70d));

    static ArrayList<DoubleProperty> widthList;

    void initTable() {
        Label label;
        Pane pane;
        for (int i = 0; i < titleList.size(); i++) {
            label = new Label(titleList.get(i));
            pane = new Pane(label);
            pane.setStyle("-fx-background-color: #FFF");
            pane.setBorder(new Border(
                    new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID
                            , CornerRadii.EMPTY, new BorderWidths(0.5, 0.5, 0.5, 0.5))));

            pane.setEffect(new DropShadow(1, Color.BLACK));
            label.layoutXProperty().bind(pane.widthProperty().subtract(label.widthProperty()).divide(2));

            pane.setPrefWidth(prefWidthList.get(i));

            box.getChildren().add(pane);
            box.setBorder(new Border(
                    new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID
                            , CornerRadii.EMPTY, new BorderWidths(0.5, 0.5, 0.5, 0.5))));

            box.setEffect(new DropShadow(1, Color.BLACK));
            box.setAlignment(Pos.CENTER_LEFT);
            widthList.add(pane.prefWidthProperty());
        }
    }

    public TableTitle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.FXMLPage.TABLE_TITLE));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        widthList = new ArrayList<>();
        initTable();

        for (int i = 0; i < box.getChildren().size(); i++) {
            Node node = box.getChildren().get(i);
            Node previousNode = null;
            if (i > 0)
                previousNode = box.getChildren().get(i - 1);

            Resizable.makeResizable(node, previousNode);
        }
    }

    public static List<DoubleProperty> getWidthList() {
        return widthList;
    }
}
