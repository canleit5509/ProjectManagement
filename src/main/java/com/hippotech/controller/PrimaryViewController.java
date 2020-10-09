package com.hippotech.controller;


import com.hippotech.controller.components.WeekTitle;
import com.hippotech.controller.components.Week;
import com.hippotech.interfaces.IAddContentToWindow;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PrimaryViewController implements Initializable {
    @FXML
    GridPane gridPane;
    @FXML
    Button btnEdit;
    @FXML
    Button btnAdd;
    @FXML
    Button btnDel;
    @FXML
    Button btnProject;
    @FXML
    Button btnPerson;

    @FXML
    VBox rightPane;
    @FXML
    VBox timeLineTitle;
    @FXML
    HBox titleBox;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    ArrayList<Task> tasks;
    @FXML
    GridPane timeLinePane;
    @FXML
    ScrollBar scBar;

    ArrayList<Double> highestHeightPerRow = new ArrayList<>();
    ArrayList<Double> heightListAllTable = new ArrayList<>();

    ModalWindowController modalWindowController = new ModalWindowController(this.getClass());

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        tasks = taskService.getAllTask();
    }

    public void initTable() {
        int numCols = 9;
        int numRows = tasks.size();


        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(35);
            rowConstraints.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Task task = tasks.get(i);
                switch (j) {
                    case 0: {
                        ProjectName projectName = projectNameService.getProjectName(task.getPrName());
                        addPane(i, j, 20, task.getPrName(), projectName.getProjectColor());
                        break;
                    }
                    case 1: {
                        addPane(i, j, 20, task.getTitle(), "");
                        break;
                    }
                    case 2: {
                        Person person = personService.getPersonByName(task.getName());
                        addPane(i, j, 20, task.getName(), person.getColor());
                        break;
                    }
                    case 3: {
                        addPane(i, j, 20, task.getStartDate(), "");
                        break;
                    }
                    case 4: {
                        addPane(i, j, 20, task.getDeadline(), "");
                        break;
                    }
                    case 5: {
                        addPane(i, j, 20, task.getFinishDate(), "");
                        break;
                    }
                    case 6: {
                        addPane(i, j, 20, task.getExpectedTime() + "", "");
                        break;
                    }
                    case 7: {
                        addPane(i, j, 20, task.getFinishTime() + "", "");
                        break;
                    }
                    case 8: {
                        addPane(i, j, 20, task.getProcessed() + "%", "");
                        break;
                    }

                }

            }
        }
        for (int h = 0; h < numRows; h++) {
            double tempMaxHeight = 0;
            for (int j = 0; j < numCols; j++) {
                if (tempMaxHeight < heightListAllTable.get(h * numCols + j)) {
                    tempMaxHeight = heightListAllTable.get(h * numCols + j);
                }
            }
            highestHeightPerRow.add(tempMaxHeight);
        }
        System.out.println("size: " + highestHeightPerRow.size());
        for (Double i : highestHeightPerRow) {
            System.out.println("height: " + i);
        }
    }


    public String getColor(LocalDate date, Task task) {
        LocalDate startDate = LocalDate.parse(task.getStartDate());
        if (date.isBefore(startDate)) return Constant.COLOR.WHITE;

        LocalDate deadLine = LocalDate.parse(task.getDeadline());
        if (task.getFinishDate() != null) {
            LocalDate finishDate = LocalDate.parse(task.getFinishDate());
            if (finishDate.isBefore(deadLine)) {
                if (date.isEqual(finishDate)) return Constant.COLOR.DARK_GREEN;
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(finishDate) || date.isEqual(startDate))
                    return Constant.COLOR.SOFT_GREEN;
            } else {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isEqual(finishDate)) return Constant.COLOR.YELLOW;
                if (finishDate.isEqual(deadLine) && finishDate.isEqual(date)) return Constant.COLOR.DARK_GREEN;
                if (date.isBefore(deadLine) || date.isEqual(startDate)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(finishDate)) return Constant.COLOR.ORANGE;

            }
        } else {
            LocalDate now = LocalDate.now();
            if (now.isBefore(deadLine)) {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(now) || date.isEqual(startDate)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.WHITE;

            } else {
                if (date.isEqual(deadLine)) return Constant.COLOR.RED;
                if (date.isBefore(now) || date.isEqual(startDate)) return Constant.COLOR.SOFT_GREEN;
                if (date.isBefore(deadLine)) return Constant.COLOR.YELLOW;
            }
        }
        return Constant.COLOR.WHITE;
    }

    private LocalDate getMonday(LocalDate date) {
        LocalDate monday;
        monday = date.minus(date.getDayOfWeek().getValue() - 1, ChronoUnit.DAYS);
        return monday;
    }

    private void addTimeline() throws ParseException {
        timeLinePane.getColumnConstraints().clear();
        timeLinePane.getChildren().clear();
        LocalDate first = getMonday(LocalDate.of(2020, 1, 1));
        LocalDate last = getMonday(LocalDate.of(2020, 12, 31)).plusDays(5);
        int colsNum = new AddTaskViewController().workDays(first, last);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(35);
        for (int i = 0; i < colsNum; i++) {
            timeLinePane.getColumnConstraints().add(columnConstraints);
        }
        RowConstraints rowConstraints = new RowConstraints();

        for (Double height :
                highestHeightPerRow) {
            rowConstraints.setPrefHeight(height);
            timeLinePane.getRowConstraints().add(rowConstraints);
        }


        for (Task task : tasks) {
            int j = 0;
            for (LocalDate i = first; !i.isEqual(last); i = i.plusDays(1)) {
                if (i.getDayOfWeek().getValue() < 6) {

                    //TODO:
                    Pane cell = new Pane();
                    cell.setBackground(new Background(new BackgroundFill(Color.valueOf(getColor(i, task)),
                            CornerRadii.EMPTY, Insets.EMPTY)));
                    Label label = new Label(i.toString().substring(5));
                    cell.getChildren().add(label);
                    timeLinePane.add(cell, j, tasks.indexOf(task));
                    System.out.println(i);
                    j++;
                }
            }
        }
    }


    private void addPane(int colIndex, int rowIndex, int labelSize, String content, String colorCode) {
        Label label = new Label("  " + content);
        label.setWrapText(true);
        Text text = new Text("  " + content);
        text.setFont(new Font(15));
        text.setWrappingWidth(250);
        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        pane.setPrefWidth(10);
        pane.setMaxWidth(10);

        if (rowIndex == 0 || rowIndex == 2) {
            pane.setStyle("-fx-background-color: #" + colorCode.substring(2) + ";");
        }
        gridPane.add(pane, rowIndex, colIndex);
        ObservableList<Node> nodeList = pane.getChildren();
        for (Node i : nodeList) {
            heightListAllTable.add(i.getLayoutBounds().getHeight());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        try {
            addTimeline();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initTimelineTitle();
        initScrollBar();
    }

    public void initScrollBar() {
//        scBar.setLayoutX(titleBox.getHeight() + rightPane.getHeight() - scBar.getHeight());
//        scBar.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                System.out.println(t1.doubleValue() + "V");
//                rightPane.setLayoutY(-t1.doubleValue());
//            }
//        });
    }
            eventHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void eventHandler() {
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = (Node) mouseEvent.getSource();
                modalWindowController.showWindowModal(node,
                        "/com/hippotech/AddTaskView.fxml",
                        Constant.WindowTitleConstant.ADD_TASK_TITLE);
                refreshTable();
            }
        });
        btnDel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Task selected = tbData.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_DELETE);
                } else {
                    Optional<ButtonType> option = _Alert.showWaitConfirmation(
                            Constant.WindowTitleConstant.DELETE_TASK_TITLE,
                            Constant.DialogConstant.CONFIRM_DELETE_TASK
                    );
                    if (option.get() == ButtonType.OK) {
                        tbData.getItems().remove(selected);
                        taskService.deleteTask(selected);
                        _Alert.showInfoNotification(Constant.WindowTitleConstant.DELETE_TASK_TITLE);
                        refreshTable();
                    }
                }
            }
        });
        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Task selected = tbData.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_UPDATE);
                }else {
                    // TODO : Add controller in a function
                FXMLLoader loader = modalWindowController.getLoader("/com/hippotech/UpdateTaskView.fxml");
                Parent parent = modalWindowController.load(loader);
                Node node = (Node) mouseEvent.getSource();

                UpdateTaskViewController controller = loader.getController();
                controller.setTask(selected);
                controller.setComboBox();

    public void initTimelineTitle() {
//        HBox pane = new HBox();
        HBox timeline = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
            WeekTitle weekTitle = new WeekTitle();
            LocalDate addDay = LocalDate.of(year, 1, 1).plusWeeks(i);
            weekTitle.setText(addDay);
            timeline.getChildren().add(weekTitle);
        }
        timeLineTitle.getChildren().add(timeline);
    }
                modalWindowController.showWindowModal(
                        node,
                        parent,
                        Constant.WindowTitleConstant.UPDATE_TASK_TITLE
                        );
                    refreshTable();
                }
            }
        });

        btnPerson.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                modalWindowController.showWindowModal(mouseEvent,
                        "/com/hippotech/PersonManagement.fxml",
                        Constant.WindowTitleConstant.PERSON_MANAGEMENT_TITLE);
                refreshTable();
            }
        });

        btnProject.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                modalWindowController.showWindowModal(e,
                        "/com/hippotech/ProjectManagement.fxml",
                        Constant.WindowTitleConstant.PROJECT_MANAGEMENT_TITLE);
                refreshTable();
            }
        });
    }
}
