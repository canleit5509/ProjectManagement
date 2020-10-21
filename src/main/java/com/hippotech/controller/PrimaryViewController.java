package com.hippotech.controller;

import com.hippotech.controller.components.WeekTitle;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
import com.hippotech.utilities.DateAndColor;
import com.hippotech.utilities._Dimension;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class PrimaryViewController implements Initializable {
    final double GRIDPANE_WIDTH = 960;
    final double SCROLL_MAX_HEIGHT = 200;
    public ScrollPane verticalScrollPane;
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
    GridPane timeLinePane;
    @FXML
    ScrollBar timeLineScrollbar;

    int numCols = 9;
    int numRows;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    ArrayList<Task> tasks;


    ArrayList<Double> highestHeightPerRow;
    ArrayList<Double> heightListAllTable;

    ModalWindowController modalWindowController = new ModalWindowController(this.getClass());
    int selectedRowIndex = -1;

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        tasks = taskService.getAllTask();
    }

    private void initTable() {
        highestHeightPerRow = new ArrayList<>();
        heightListAllTable = new ArrayList<>();
        gridPane.getChildren().clear();
        numCols = 9;
        numRows = tasks.size();
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(35);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // Fill data to table
        for (int i = 0; i < numRows; i++) {
            Task task = tasks.get(i);
            ArrayList<String> taskObj = valuesForTaskRow(task);
            // Pick color
            ProjectName projectName = projectNameService.getProjectName(task.getPrName());
            Person person = personService.getPersonByName(task.getName());
            String color;
            for (int j = 0; j < numCols; j++) {
                if (j == 0) color = projectName.getProjectColor();
                else if (j == 2) color = person.getColor();
                else color = Constant.COLOR.WHITE;
                addPane(i, j, taskObj.get(j), color);
            }
        }
        // Get height of rows
        for (int i = 0; i < numRows; i++) {
            double tempMaxHeight = 0;
            for (int j = 0; j < numCols; j++) {
                if (tempMaxHeight < heightListAllTable.get(i * numCols + j)) {
                    tempMaxHeight = heightListAllTable.get(i * numCols + j);
                }
            }
            highestHeightPerRow.add(tempMaxHeight);
        }
        System.out.println("------------------");
        eventHandler();
    }

    private ArrayList<String> valuesForTaskRow(Task task) {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(task.getPrName());
        arrayList.add(task.getTitle());
        arrayList.add(task.getName());
        arrayList.add(task.getStartDate());
        arrayList.add(task.getDeadline());
        arrayList.add(task.getFinishDate());
        arrayList.add(task.getExpectedTime() + "");
        arrayList.add(task.getFinishTime() + "");
        arrayList.add(task.getProcessed() + "%");

        return arrayList;
    }

    private void addTimeline() {
        timeLinePane.setViewOrder(1);
        timeLinePane.getColumnConstraints().clear();
        timeLinePane.getChildren().clear();
        LocalDate first = DateAndColor.getMonday(LocalDate.of(2020, 1, 1));
        LocalDate last = DateAndColor.getMonday(LocalDate.of(2020, 12, 31));
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(35 * 260);
        timeLinePane.getColumnConstraints().add(columnConstraints);
        RowConstraints rowConstraints = new RowConstraints();
        int j = 0;
        for (Double height : highestHeightPerRow) {
            rowConstraints.setMinHeight(30);
            timeLinePane.getRowConstraints().add(rowConstraints);
            addTimelineRow(height, tasks.get(j), first, last);
            j++;
        }
    }

    private void addTimelineRow(double height, Task task, LocalDate first, LocalDate last) {
        HBox pane = new HBox();
        for (LocalDate i = first; !i.isEqual(last); i = i.plusDays(1)) {
            if (i.getDayOfWeek().getValue() < 6) {
                // TODO:
                Rectangle rect = new Rectangle(35, height);
                if (height > 20) {
                    rect.setHeight(height);
                } else {
                    rect.setHeight(height + 15);
                }
                rect.setStrokeType(StrokeType.INSIDE);
                rect.setStroke(Color.valueOf("#000000"));
                rect.setStrokeWidth(0.5);
                rect.setFill(Color.valueOf(DateAndColor.getColor(i, task)));
                pane.getChildren().add(rect);
            }
        }
        timeLinePane.add(pane, 0, tasks.indexOf(task));
    }

    private void addPane(int rowIndex, int colIndex, String content, String colorCode) {
        Label label = new Label("  " + content);
        label.setWrapText(true);

        Text text = new Text("  " + content);
        text.setFont(new Font(15));
        if (colIndex == 1) text.setWrappingWidth(260);

        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        pane.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID
                        , CornerRadii.EMPTY, new BorderWidths(0.5, 0.5, 0.5, 0.5))));

        pane.setEffect(new DropShadow(1, Color.BLACK));
        if (colIndex == 0 || colIndex == 2) {
            pane.setStyle("-fx-background-color: #" + colorCode.substring(2) + ";");
        } else
            pane.setStyle("-fx-background-color: " + colorCode + ";");
        gridPane.add(pane, colIndex, rowIndex);

        ObservableList<Node> nodeList = pane.getChildren();
        for (Node i : nodeList) {
            heightListAllTable.add(i.getLayoutBounds().getHeight());
        }
        if (colIndex == 0)
            text.setWrappingWidth(100);
        if (colIndex == 2)
            text.setWrappingWidth(90);
        if (colIndex == 8)
            text.setWrappingWidth(70);
    }

    private void eventHandler() {
        ObservableList<Node> nodes = gridPane.getChildren();
        for (Node node : gridPane.getChildren()) {
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                selectedRowIndex = (nodes.indexOf(node) - 1) / numCols;
                System.out.println(selectedRowIndex);

                tasks = taskService.getAllTask();
                initTable();
                node.setOnMouseClicked(event -> gridPane.getChildren().forEach(c -> {
                    Integer targetIndex = GridPane.getRowIndex(node);
                    if (gridPane.getRowIndex(c) == targetIndex) {
                        if (gridPane.getColumnIndex(c) != 0 && gridPane.getColumnIndex(c) != 2) {
                            tasks = taskService.getAllTask();
                            c.setStyle("-fx-background-color:#8896DE;");
                        }
                    }
                }));

            });
        }
        btnAdd.setOnMouseClicked(mouseEvent -> {
            Node node = (Node) mouseEvent.getSource();
            modalWindowController.showWindowModal(node, "/com/hippotech/AddTaskView.fxml",
                    Constant.WindowTitleConstant.ADD_TASK_TITLE);
            tasks = taskService.getAllTask();
            initTable();
            addTimeline();
        });
        btnEdit.setOnMouseClicked(mouseEvent -> {
            Node node = (Node) mouseEvent.getSource();
            if (selectedRowIndex == -1) {
                _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_UPDATE);
            } else {
                Task selected = tasks.get(selectedRowIndex);
                FXMLLoader loader = modalWindowController.getLoader("/com/hippotech/UpdateTaskView.fxml");
                Parent parent = modalWindowController.load(loader);
                UpdateTaskViewController controller = loader.getController();
                controller.setTask(selected);
                controller.setComboBox();
                modalWindowController.showWindowModal(
                        node,
                        parent,
                        Constant.WindowTitleConstant.UPDATE_TASK_TITLE
                );
                tasks = taskService.getAllTask();
                initTable();
                addTimeline();
            }
            selectedRowIndex = -1;
        });
        btnDel.setOnMouseClicked(mouseEvent -> {
            if (selectedRowIndex == -1) {
                _Alert.showWaitInfoWarning(Constant.DialogConstant.CHOOSE_A_TASK_TO_DELETE);
            } else {
                Task selected = tasks.get(selectedRowIndex);
                if (selected != null) {
                    Optional<ButtonType> option = _Alert.showWaitConfirmation(Constant.DialogConstant.WARNING_TITLE,
                            Constant.DialogConstant.CONFIRM_DELETE_TASK);
                    if (option.get() == ButtonType.OK) {
                        taskService.deleteTask(tasks.get(selectedRowIndex));
                        _Alert.showInfoNotification(Constant.WindowTitleConstant.DELETE_TASK_TITLE);
                        tasks = taskService.getAllTask();
                        initTable();
                        addTimeline();
                    }
                }
            }
            selectedRowIndex = -1;
        });
        btnPerson.setOnMouseClicked(mouseEvent -> {
            modalWindowController.showWindowModal(mouseEvent,
                    "/com/hippotech/PersonManagement.fxml",
                    Constant.WindowTitleConstant.PERSON_MANAGEMENT_TITLE);
            tasks = taskService.getAllTask();
            initTable();
        });

        btnProject.setOnMouseClicked(e -> {
            modalWindowController.showWindowModal(e,
                    "/com/hippotech/ProjectManagement.fxml",
                    Constant.WindowTitleConstant.PROJECT_MANAGEMENT_TITLE);
            tasks = taskService.getAllTask();
            initTable();
        });
    }

    private void initTimelineTitle() {
        HBox timeline = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
            WeekTitle weekTitle = new WeekTitle();
            LocalDate addDay = LocalDate.of(year, 1, 1).plusWeeks(i);
            weekTitle.setText(addDay);
            timeline.getChildren().add(weekTitle);
        }
        timeLineTitle.getChildren().add(timeline);
        timeLineTitle.setMaxWidth(new _Dimension().getMaxScreenWidth() - GRIDPANE_WIDTH);
        // timeLineTitle set back
        timeLineTitle.setViewOrder(3);
    }

    private void initScrollBar() {
        timeLineScrollbar.setPrefWidth(new _Dimension().getMaxScreenWidth() - GRIDPANE_WIDTH);
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
        timeLineScrollbar.valueProperty().addListener((observableValue, number, newValue) -> {
            if (ref.timeLineTitleWidth == 0) return;

            ARHScrollValue.set(newValue.doubleValue());
            ref.hScrollValue = ARHScrollValue.get();
            double translateX;
            double timeLineTitleMaxWidth = (new _Dimension().getMaxScreenWidth() - GRIDPANE_WIDTH);
            translateX = ref.hScrollValue * (ref.timeLineTitleWidth - timeLineTitleMaxWidth) / 100.0;

            timeLinePane.setTranslateX(-translateX);
            timeLineTitle.setTranslateX(-translateX);
        });
    }

    private void initVerticalScrollBar() {
        verticalScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        verticalScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        verticalScrollPane.setMaxWidth(new _Dimension().getMaxScreenWidth());
        verticalScrollPane.setClip(new Rectangle(new _Dimension().getMaxScreenWidth(), SCROLL_MAX_HEIGHT));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        addTimeline();
        eventHandler();
        initTimelineTitle();
        timeLinePane.setMaxWidth(timeLineTitle.getMaxWidth());
        initVerticalScrollBar();
        initScrollBar();
    }
}
