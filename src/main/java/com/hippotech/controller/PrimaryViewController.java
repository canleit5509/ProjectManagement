package com.hippotech.controller;

import com.hippotech.controller.components.TableTitle;
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
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class PrimaryViewController implements Initializable {
    double GRID_PANE_WIDTH;
    public ScrollPane verticalScrollPane;
    @FXML
    TableTitle tableTitle;
    @FXML
    GridPane gridPane;
    @FXML
    HBox buttonsHBox;
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
    Pane nullPane;
    @FXML
    ScrollBar timeLineScrollbar;
    @FXML
    Button btnToday;
    @FXML
    Button currYear;
    @FXML
    Button nextYear;
    @FXML
    Button prevYear;
    _Dimension dimension;
    int sortColumn = 1;
    int year = LocalDate.now().getYear();
    int numCols = 9;
    int numRows;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;

    ObservableList<Node> gridPaneNode;
    ArrayList<Task> tasks;
    ArrayList<Double> highestHeightPerRow;
    ArrayList<Double> heightListAllTable;
    ArrayList<DoubleProperty> widthColsTableTitle;
    ArrayList<Double> defaultWidthGridPane = new ArrayList<>(Arrays.asList(100d, 260d, 90d, 90d, 90d, 90d, 85d, 85d, 70d));

    ModalWindowController modalWindowController = new ModalWindowController(this.getClass());
    int selectedRowIndex = -1;

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        dimension = new _Dimension();
        tasks = taskService.getAllTaskBy(sortColumn);

    }

    private void initTable() {

        numCols = 9;
        numRows = tasks.size();
        GRID_PANE_WIDTH = 960;
        widthColsTableTitle = (ArrayList<DoubleProperty>) TableTitle.getWidthList();

        highestHeightPerRow = new ArrayList<>();
        heightListAllTable = new ArrayList<>();

        gridPane.getChildren().clear();

        tableTitle.setMinWidth(GRID_PANE_WIDTH);
        autosizeNullPane();
        for (int i = 0; i < widthColsTableTitle.size(); i++) {
            int finalI = i;
            widthColsTableTitle.get(i).addListener((observableValue, number, t1) -> {
                setGridColWidth(finalI, (Double) t1);
                GRID_PANE_WIDTH = gridPane.boundsInLocalProperty().get().getWidth();
                handleChangeGridPaneWidth();
            });
        }


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
                else if (j == 8) {
                    int percentage = 100 - task.getProcessed();
                    double red = percentage / 100.0;
                    double blue = percentage / 100.0;
                    Color c = new Color(red, 1, blue, 1);
                    color = "#" + c.toString().substring(2);
                } else {
                    color = Constant.COLOR.WHITE;
                }
                addPane(i, j, taskObj.get(j), color);
            }
        }
        findHighestHeightPerRow();
        gridPaneItemEventHandler();
    }

    private void handleChangeGridPaneWidth() {
        tableTitle.setMinWidth(GRID_PANE_WIDTH);
        timeLineTitle.setMaxWidth(dimension.getMaxScreenWidth() - GRID_PANE_WIDTH);
        refreshTimeLineHeight();
        autosizeNullPane();
        initScrollBar();
    }

    private void findHighestHeightPerRow() {
        ObservableList<Node> nodeList = gridPane.getChildren();
        highestHeightPerRow.clear();
        double temp;
        for (int i = 0; i < numRows; i++) {
            double tempMaxHeight = 0;
            for (int j = 0; j < numCols; j++) {
                temp = nodeList.get(i * numCols + j).getBoundsInLocal().getHeight();
                if (tempMaxHeight < temp) {
                    tempMaxHeight = temp;
                }
            }
            highestHeightPerRow.add(tempMaxHeight);
        }
    }

    private void setGridColWidth(int colI, double width) {
        gridPane.getColumnConstraints().get(colI).setPrefWidth(width);
        for (int i = 0; i < numRows; i++) {
            ((Text) ((StackPane) gridPane.getChildren().get(colI + i * numCols)).getChildren().get(0)).setWrappingWidth(width);
        }
    }

    private void autosizeNullPane() {
        nullPane.setPrefWidth(GRID_PANE_WIDTH);
    }


    private ArrayList<String> valuesForTaskRow(Task task) {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(task.getPrName());
        arrayList.add(task.getTitle());
        arrayList.add(task.getName());
        arrayList.add(task.getStartDate());
        arrayList.add(task.getDeadline());
        arrayList.add(task.getFinishDate() != null ? task.getFinishDate() : "");
        arrayList.add(task.getExpectedTime() + "");
        arrayList.add(task.getFinishTime() + "");
        arrayList.add(task.getProcessed() + "%");

        return arrayList;
    }

    private void initTimeline() {
        timeLinePane.getColumnConstraints().clear();
        timeLinePane.getRowConstraints().clear();
        timeLinePane.getChildren().clear();
        LocalDate first = DateAndColor.getMonday(LocalDate.of(year, 1, 1));
        LocalDate last = DateAndColor.getMonday(LocalDate.of(year, 12, 31));
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
                Rectangle rect = new Rectangle(35, height);
                if (height > 20) {
                    rect.setHeight(Math.ceil(height) + 1);
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

    private void refreshTimeLineHeight() {
        findHighestHeightPerRow();
        for (int i = 0; i < numRows; i++) {
            setHeightTimeLineRow(i, highestHeightPerRow.get(i));
        }
    }

    private void setHeightTimeLineRow(int rowI, double height) {
        for (int i = 0; i < 52 * 5; i++) {
            ((Rectangle) ((HBox) timeLinePane.getChildren().get(rowI)).getChildren().get(i)).setHeight(height);
        }
    }

    private void addPane(int rowIndex, int colIndex, String content, String colorCode) {
        Text text = new Text("  " + content);
        text.setFont(new Font(15));
        if (colIndex == 1) text.setWrappingWidth(defaultWidthGridPane.get(1));
        if (colIndex == 8) text.setTextAlignment(TextAlignment.CENTER);

        StackPane pane = new StackPane();
        pane.setPrefWidth(defaultWidthGridPane.get(colIndex));
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
        if (colIndex == 0)
            text.setWrappingWidth(defaultWidthGridPane.get(0));
        if (colIndex == 2)
            text.setWrappingWidth(defaultWidthGridPane.get(2));
        if (colIndex == 8)
            text.setWrappingWidth(defaultWidthGridPane.get(8));
    }

    private void colorSelected(Node node) {
        // Init
        if (selectedRowIndex == -1)
            selectedRowIndex = GridPane.getRowIndex(node);

        int dropShadowSpread = 15;
        gridPaneNode = gridPane.getChildren();
        Node timeLineNode = timeLinePane.getChildren().get(selectedRowIndex);
        // Deselect previous selected row
        for (int i = 0; i < numCols; i++) {
            if (i != 0 && i != 2 && i != 8) {
                gridPaneNode.get(selectedRowIndex * numCols + i).setStyle("-fx-background-color:#FFFFFF;");
            }
        }

        timeLineNode.setEffect(null);
        timeLineNode.setViewOrder(0);

        // Paint new row
        selectedRowIndex = GridPane.getRowIndex(node);
        // Paint current row
        for (int i = 0; i < numCols; i++) {
            if (i != 0 && i != 2 && i != 8) {
                gridPaneNode.get(selectedRowIndex * numCols + i).setStyle("-fx-background-color:#8896DE;");
            }
        }
        timeLineNode = timeLinePane.getChildren().get(selectedRowIndex);

        timeLineNode.setEffect(new DropShadow(dropShadowSpread, Color.BLUE));
        timeLineNode.setViewOrder(-1);
    }

    private void gridPaneItemEventHandler() {
        gridPaneNode = gridPane.getChildren();
        for (Node node : gridPaneNode) {
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> colorSelected(node));
        }
    }

    private void eventHandler() {
        nextYear.setOnMouseClicked(e -> {
            year++;
            switchYear();
        });
        prevYear.setOnMouseClicked(e -> {
            year--;
            switchYear();
        });
        btnAdd.setOnMouseClicked(mouseEvent -> {
            Node node = (Node) mouseEvent.getSource();
            modalWindowController.showWindowModal(node, "/com/hippotech/AddTaskView.fxml",
                    Constant.WindowTitleConstant.ADD_TASK_TITLE);
            tasks = taskService.getAllTaskBy(sortColumn);
            initTable();
            initTimeline();
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
                tasks = taskService.getAllTaskBy(sortColumn);
                initTable();
                initTimeline();
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
                        tasks = taskService.getAllTaskBy(sortColumn);
                        initTable();
                        initTimeline();
                    }
                }
            }
            selectedRowIndex = -1;
        });
        btnPerson.setOnMouseClicked(mouseEvent -> {
            modalWindowController.showWindowModal(mouseEvent,
                    "/com/hippotech/PersonManagement.fxml",
                    Constant.WindowTitleConstant.PERSON_MANAGEMENT_TITLE);
            tasks = taskService.getAllTaskBy(sortColumn);
            initTable();
        });

        btnProject.setOnMouseClicked(e -> {
            modalWindowController.showWindowModal(e,
                    "/com/hippotech/ProjectManagement.fxml",
                    Constant.WindowTitleConstant.PROJECT_MANAGEMENT_TITLE);
            tasks = taskService.getAllTaskBy(sortColumn);
            initTable();
        });
        btnToday.setOnAction(e -> {
            year = 2020;
            switchYear();
        });
        var ref = new Object() {
            double oldX;

        };
        tableTitle.getIndex().addListener((observable, oldValue, newValue) -> {

            sortColumn = newValue.intValue();
            tasks = taskService.getAllTaskBy((Integer) newValue);
            initTable();
            initTimeline();
        });
        timeLinePane.setOnMousePressed(e -> {
            ref.oldX = e.getX();
            System.out.println(ref.oldX);
        });

        timeLinePane.setOnMouseDragged(e -> {
            double xPercentOld = timeLineScrollbar.getValue();
            double newX = e.getX();
            double distance = newX - ref.oldX;
            double percentDistance = 0;
            if (distance < 0) {
                percentDistance = 9 * Math.abs(distance) / Constant.TIMELINEPANESPECS.GRIDPANETOTALWIDTH;
            } else {
                percentDistance = -9 * Math.abs(distance) / Constant.TIMELINEPANESPECS.GRIDPANETOTALWIDTH;
            }
            timeLineScrollbar.setValue(percentDistance + xPercentOld);
//            timeLineScrollbar.setValue((e.getX()/Constant.TIMELINEPANESPECS.GRIDPANETOTALWIDTH)*100);

        });

    }

    private void switchYear() {
        currYear.setText(String.valueOf(year));
        initTimeline();
        initTimelineTitle();
        refreshAutoScroll();
        if (year != LocalDate.now().getYear()) {
            timeLineTitle.setTranslateX(0);
            timeLinePane.setTranslateX(0);
            timeLineScrollbar.setValue(0);
        }
    }

    private void initTimelineTitle() {
        HBox timeline = new HBox();
        for (int i = 0; i < 52; i++) {
            WeekTitle weekTitle = new WeekTitle();
            LocalDate addDay = LocalDate.of(year, 1, 1).plusWeeks(i);
            weekTitle.setText(addDay);
            timeline.getChildren().add(weekTitle);
        }
        timeLineTitle.getChildren().clear();
        timeLineTitle.getChildren().add(timeline);
        timeLineTitle.setMaxWidth(dimension.getMaxScreenWidth() - GRID_PANE_WIDTH);
    }

    private void initScrollBar() {
        timeLineScrollbar.setPrefWidth(dimension.getMaxScreenWidth() - GRID_PANE_WIDTH);
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
            double timeLineTitleMaxWidth = (dimension.getMaxScreenWidth() - GRID_PANE_WIDTH);
            translateX = ref.hScrollValue * (ref.timeLineTitleWidth - timeLineTitleMaxWidth) / 100.0;

            timeLinePane.setTranslateX(-translateX);
            timeLineTitle.setTranslateX(-translateX);
        });
    }

    private void initVerticalScrollBar() {
        verticalScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        verticalScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        verticalScrollPane.setMaxWidth(dimension.getMaxScreenWidth());
        double scrollPaneHeight = dimension.getMaxScreenHeight()
                - gridPane.getLayoutBounds().getMaxY()
                - buttonsHBox.getLayoutBounds().getHeight();
        verticalScrollPane.setPrefHeight(scrollPaneHeight);
        verticalScrollPane.setClip(new Rectangle(dimension.getMaxScreenWidth(),
                scrollPaneHeight));
    }

    public void refreshAutoScroll() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate dateNow = LocalDate.now();
        int weekNumberNow = dateNow.get(weekFields.weekOfWeekBasedYear());
        double timelineScrollBarValue = (weekNumberNow / 52.0) * 100;// + Constant.TIMELINEPANESPECS.OFFSETAUTOSCROLLTIMELINE;
        double timeLineTitleMaxWidth = (dimension.getMaxScreenWidth() - GRID_PANE_WIDTH);
        double translateX = timelineScrollBarValue * (Constant.TIMELINEPANESPECS.GRIDPANETOTALWIDTH - timeLineTitleMaxWidth) / 100.0;
        timeLineScrollbar.setValue(timelineScrollBarValue);
        timeLinePane.setTranslateX(-translateX);
        timeLineTitle.setTranslateX(-translateX);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currYear.setText(String.valueOf(year));

        initTable();
        initTimeline();
        initTimelineTitle();
        timeLinePane.setMaxWidth(timeLineTitle.getMaxWidth());
        initVerticalScrollBar();
        initScrollBar();
        refreshAutoScroll();
        eventHandler();

    }
}
