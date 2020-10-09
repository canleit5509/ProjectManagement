package com.hippotech.controller;


import com.hippotech.controller.components.TableTitle;
import com.hippotech.controller.components.Week;
import com.hippotech.model.Person;
import com.hippotech.model.ProjectName;
import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.RED;


public class PrimaryViewController implements Initializable {
    private final ObservableList<Task> listTask;
    @FXML
    GridPane gridPane;
    @FXML
    Button btnEdit;
    @FXML
    Button btnAdd;
    @FXML
    Button btnDel;
    @FXML
    ScrollPane rightPane;
    @FXML
    VBox leftPaneTitle;
    TaskService taskService;
    PersonService personService;
    ProjectNameService projectNameService;
    ArrayList<Task> tasks;
    ArrayList<Double> heightestHeightPerRow = new ArrayList<>();
    ArrayList<Double> heightListAllTable = new ArrayList<>();

    public PrimaryViewController() {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        listTask = FXCollections.observableArrayList(taskService.getAllTask());
        tasks = taskService.getAllTask();
    }

    public void initTable() {
        ArrayList<Task> listTask2 = taskService.getAllTask();
        int numCols = 9;
        int numRows = listTask.size();


        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(35);
            rowConstraints.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Task task = listTask2.get(i);
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
            heightestHeightPerRow.add(tempMaxHeight);
        }
        System.out.println("size: " + heightestHeightPerRow.size());
        for (Double i : heightestHeightPerRow) {
            System.out.println("height: " + i);
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
        refreshTimeline();
    }

    public void refreshTimeline() {
        HBox pane = new HBox();
        int year = 2020;
        for (int i = 0; i < 52; i++) {
            Week week = new Week(LocalDate.of(year, 1, 1).plusWeeks(i), tasks);
            pane.getChildren().add(week);
        }
        rightPane.setContent(pane);

        TableTitle tableTitle = new TableTitle();
        leftPaneTitle.getChildren().add(tableTitle);
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
//                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//                FXMLLoader loader = getLoader("/com/hippotech/UpdateTaskView.fxml");
//                Parent updateTaskParent = null;
//                try {
//                    updateTaskParent = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Scene scene = new Scene(updateTaskParent);
//                UpdateTaskViewController controller = loader.getController();
//                Task selected = tbData.getSelectionModel().getSelectedItem();
//                if (selected == null) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Warning");
//                    alert.setHeaderText("Vui lòng chọn công việc cần chỉnh sửa");
//                    alert.show();
//                } else {
//                    controller.setTask(selected);
//                    controller.setComboBox();
//                    Stage updateTaskWindow = new Stage();
//                    updateTaskWindow.setTitle("Chỉnh sửa công việc");
//                    updateTaskWindow.setScene(scene);
//                    updateTaskWindow.initModality(Modality.WINDOW_MODAL);
//                    updateTaskWindow.initOwner(stage);
//                    updateTaskWindow.showAndWait();
//                    refreshTable();
//                    refreshTimeline();
//                }
//            }
//        });
//        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/com/hippotech/UpdateTaskView.fxml"));
//                Parent updateTaskParent = null;
//                try {
//                    updateTaskParent = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Scene scene = new Scene(updateTaskParent);
//                UpdateTaskViewController controller = loader.getController();
//                Task selected = tbData.getSelectionModel().getSelectedItem();
//                if (selected == null) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Warning");
//                    alert.setHeaderText("Vui lòng chọn công việc cần chỉnh sửa");
//                    alert.show();
//                } else {
//                    controller.setTask(selected);
//                    controller.setComboBox();
//                    Stage updateTaskWindow = new Stage();
//                    updateTaskWindow.setTitle("Chỉnh sửa công việc");
//                    updateTaskWindow.setScene(scene);
//                    updateTaskWindow.initModality(Modality.WINDOW_MODAL);
//                    updateTaskWindow.initOwner(stage);
//                    updateTaskWindow.showAndWait();
//                    refreshTable();
//                    refreshTimeline();
//                }
//            }
//        });
//    }
//
//    public FXMLLoader getLoader(String source) {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource(source));
//        return loader;
//    }
//
//
//    public void Delete(ActionEvent e) {
//        Task selected = tbData.getSelectionModel().getSelectedItem();
//        if (selected == null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Warning");
//            alert.setHeaderText("Vui lòng chọn công việc cần xóa");
//            alert.show();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Xóa công việc");
//            alert.setHeaderText("Bạn có chắc chắn muốn xóa công việc này?");
//            Optional<ButtonType> option = alert.showAndWait();
//            if (option.get() == null) {
//            } else if (option.get() == ButtonType.OK) {
//                tbData.getItems().remove(selected);
//                taskService.deleteTask(selected);
//                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
//                alert2.setTitle("Thông báo");
//                alert2.setHeaderText("Đã xóa");
//                alert2.show();
//                refreshTable();
//                refreshTimeline();
//            } else if (option.get() == ButtonType.CANCEL) {
//            }
//        }
//    }
//
//    public void btnProject(ActionEvent e) throws IOException {
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/com/hippotech/ProjectManagement.fxml"));
//        Parent addTaskParent = loader.load();
//        Scene scene = new Scene(addTaskParent);
//        Stage addTaskWindow = new Stage();
//        addTaskWindow.setTitle("Quản lý nhân sự");
//        addTaskWindow.setScene(scene);
//        addTaskWindow.initModality(Modality.WINDOW_MODAL);
//        addTaskWindow.initOwner(stage);
//        addTaskWindow.showAndWait();
//        refreshTable();
//        refreshTimeline();
//    }
//
//    public void btnPerson(ActionEvent e) throws IOException {
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/com/hipptech/PersonManagement.fxml"));
//        Parent addTaskParent = loader.load();
//        Scene scene = new Scene(addTaskParent);
//        Stage addTaskWindow = new Stage();
//        addTaskWindow.setTitle("Quản lý nhân sự");
//        addTaskWindow.setScene(scene);
//        addTaskWindow.initModality(Modality.WINDOW_MODAL);
//        addTaskWindow.initOwner(stage);
//        addTaskWindow.showAndWait();
//        refreshTable();
//        refreshTimeline();
//    }

}
