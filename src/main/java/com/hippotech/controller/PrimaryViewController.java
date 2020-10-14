package com.hippotech.controller;


import com.hippotech.controller.components.WeekTitle;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


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
    VBox timeLineTitle;
    @FXML
    HBox titleBox;
    @FXML
    ScrollPane timeLineScrollPane;
    @FXML
    GridPane timeLinePane;

    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    ArrayList<Task> tasks;

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
            Task task = tasks.get(i);
            ArrayList<String> taskObj = valuesForTaskRow(task);
            ProjectName projectName = projectNameService.getProjectName(task.getPrName());
            Person person = personService.getPersonByName(task.getName());
            String color = "";
            for (int j = 0; j < numCols; j++) {
                if (j == 0) color = projectName.getProjectColor();
                else if (j == 2) color = person.getColor();
                else color = "";
                addPane(i, j, 15, taskObj.get(j), color);
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

    private ArrayList<String> valuesForTaskRow(Task task) {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(task.getPrName());
        arrayList.add(task.getTitle());
        arrayList.add(task.getName());
        arrayList.add(task.getStartDate());
        arrayList.add(task.getDeadline());
        arrayList.add(task.getFinishDate());
        arrayList.add(task.getExpectedTime()+"");
        arrayList.add(task.getFinishTime()+"");
        arrayList.add(task.getProcessed()+"%");

        return arrayList;
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
                    j++;
                }
            }
        }
    }


    private void addPane(int rowIndex, int colIndex, int labelSize, String content, String colorCode) {
        Label label = new Label("  " + content);
        label.setWrapText(true);
        Text text = new Text("  " + content);
        text.setFont(new Font(labelSize));
        text.setWrappingWidth(250);
        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        pane.setPrefWidth(10);
        pane.setMaxWidth(10);

        if (colIndex == 0 || colIndex == 2) {
            pane.setStyle("-fx-background-color: #" + colorCode.substring(2) + ";");
        }
        gridPane.add(pane, colIndex, rowIndex);
        ObservableList<Node> nodeList = pane.getChildren();
        for (Node i : nodeList) {
            heightListAllTable.add(i.getLayoutBounds().getHeight());
        }
        if(colIndex==0)
            text.setWrappingWidth(100);
        if(colIndex==2)
            text.setWrappingWidth(90);
    }



    private void eventHandler() {
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = (Node) mouseEvent.getSource();
                modalWindowController.showWindowModal(node,
                        "/com/hippotech/AddTaskView.fxml",
                        Constant.WindowTitleConstant.ADD_TASK_TITLE);
            }
        });
    }

//    private void eventHandler() {
//        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//                FXMLLoader loader = getLoader("/com/hippotech/AddTaskView.fxml");
//                Parent addTaskParent = null;
//                try {
//                    addTaskParent = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Scene scene = new Scene(addTaskParent);
//                Stage addTaskWindow = new Stage();
//                addTaskWindow.setTitle("Thêm công việc");
//                addTaskWindow.setScene(scene);
//                addTaskWindow.initModality(Modality.WINDOW_MODAL);
//                addTaskWindow.initOwner(stage);
//                addTaskWindow.showAndWait();
//                refreshTable();
//                refreshTimeline();
//            }
//        });
//        btnDel.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Task selected = tbData.getSelectionModel().getSelectedItem();
//                if (selected == null) {
//                    _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_DELETE);
//                } else {
//                    Optional<ButtonType> option = _Alert.showWaitConfirmation(
//                            Constant.WindowTitleConstant.DELETE_TASK_TITLE,
//                            Constant.DialogConstant.CONFIRM_DELETE_TASK
//                    );
//                    if (option.get() == ButtonType.OK) {
//                        tbData.getItems().remove(selected);
//                        taskService.deleteTask(selected);
//                        _Alert.showInfoNotification(Constant.WindowTitleConstant.DELETE_TASK_TITLE);
//                        refreshTable();
//                    }
//                }
//            }
//        });
//        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Task selected = tbData.getSelectionModel().getSelectedItem();
//                if (selected == null) {
//                    _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_UPDATE);
//                }else {
//                    // TODO : Add controller in a function
//                FXMLLoader loader = modalWindowController.getLoader("/com/hippotech/UpdateTaskView.fxml");
//                Parent parent = modalWindowController.load(loader);
//                Node node = (Node) mouseEvent.getSource();
//
//                UpdateTaskViewController controller = loader.getController();
//                controller.setTask(selected);
//                controller.setComboBox();
//
//
//                modalWindowController.showWindowModal(
//                        node,
//                        parent,
//                        Constant.WindowTitleConstant.UPDATE_TASK_TITLE
//                        );
//                }
//            }
//        });
//
//        btnPerson.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                modalWindowController.showWindowModal(mouseEvent,
//                        "/com/hippotech/PersonManagement.fxml",
//                        Constant.WindowTitleConstant.PERSON_MANAGEMENT_TITLE);
//                refreshTable();
//            }
//        });
//
//        btnProject.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                modalWindowController.showWindowModal(e,
//                        "/com/hippotech/ProjectManagement.fxml",
//                        Constant.WindowTitleConstant.PROJECT_MANAGEMENT_TITLE);
//                refreshTable();
//            }
//        });
//    }
//    }

    public void initTimelineTitle() {
        HBox timeline = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
            WeekTitle weekTitle = new WeekTitle();
            LocalDate addDay = LocalDate.of(year, 1, 1).plusWeeks(i);
            weekTitle.setText(addDay);
            timeline.getChildren().add(weekTitle);
        }
        timeLineTitle.getChildren().add(timeline);
        // timeLineTitle set back
        timeLineTitle.setViewOrder(5);
    }

    public void initScrollBar() {
        AtomicInteger AITimeLineTitleWidth = new AtomicInteger();
        AtomicReference<Double> ARHScrollValue = new AtomicReference<>((double) 0);
        // Ref object
        var ref = new Object() {
            double hScrollValue;
            int timeLineTitleWidth = 0;

        };

        // Get timeline title width
        timeLineTitle.widthProperty().addListener((observableValue, number, t1) -> {
            AITimeLineTitleWidth.set(t1.intValue());
            ref.timeLineTitleWidth = AITimeLineTitleWidth.get();
        });

        // Get scroll value and set offset for timeLineTitle
        timeLineScrollPane.hvalueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (ref.timeLineTitleWidth == 0) return;
            ARHScrollValue.set(newValue.doubleValue());
            ref.hScrollValue = (double) ARHScrollValue.get();
            int offset;

            offset = (int) Math.floor(ref.hScrollValue* ref.timeLineTitleWidth);
            timeLineTitle.setTranslateX(-offset);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        try {
            addTimeline();
            eventHandler();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initTimelineTitle();
        timeLineScrollPane.setMaxWidth(timeLineTitle.getMaxWidth());
        initScrollBar();
    }
}