package com.hippotech.controller;


import com.hippotech.model.Task;
import com.hippotech.service.PersonService;
import com.hippotech.service.ProjectNameService;
import com.hippotech.service.TaskService;
import com.hippotech.utilities.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class AddTaskViewController implements Initializable {
    @FXML
    Label id;
    @FXML
    ComboBox<String> prName;
    @FXML
    ComboBox<String> name;
    @FXML
    TextField title;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker deadline;
    @FXML
    DatePicker finishDate;
    @FXML
    Label expectedTime;
    @FXML
    Label finishTime;
    @FXML
    Label processed;
    private PersonService personService;
    private ProjectNameService projectNameService;
    private TaskService taskService;
    private final ModalWindowController modalWindowController = new ModalWindowController(this.getClass());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskService = new TaskService();
        personService = new PersonService();
        projectNameService = new ProjectNameService();
        setComboBox();
        Random random = new Random();
        int taskID = random.nextInt(899999) + 100000;
        id.setText(String.valueOf(taskID));
        processed.setText("0%");
    }

    public boolean validate() {
        if (prName.getValue() == null) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_A_PROJECT);
            return false;
        }
        if (name.getValue() == null) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_A_PERSON);
            return false;
        }
        if (title.getText().equals("")) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_TASK_TITLE);
            return false;
        }
        if (startDate.getValue() == null) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_START_DATE);
            return false;
        }
        if (deadline.getValue() == null) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.CHOOSE_DEADLINE);
            return false;
        }
        return true;
    }

    public Task getTask() {
        String tempName = name.getValue();
        if (tempName.contains("|")) tempName = tempName.substring(7);

        Task task = new Task();
        task.setId(id.getText());
        task.setPrName(prName.getValue());
        task.setName(tempName);
        task.setTitle(title.getText());
        task.setStartDate(String.valueOf(startDate.getValue()));
        task.setDeadline(String.valueOf(deadline.getValue()));
        if (!String.valueOf(finishDate.getValue()).equals("null")) {
            task.setFinishDate(String.valueOf(finishDate.getValue()));
        }
        task.setExpectedTime(Integer.parseInt(expectedTime.getText()));
        if (!finishTime.getText().equals("")) {
            task.setFinishTime(Integer.parseInt(finishTime.getText()));
        }
        if (!processed.getText().equals("")) {
            //TODO: remove % from label processed
            task.setProcessed(Integer.parseInt(processed.getText().substring(0,processed.getText().length()-1)));
        }
        return task;
    }

    public void setComboBox() {
        ObservableList<String> prList = FXCollections.observableArrayList(projectNameService.getAllDoingProjectName());
        ObservableList<String> personList = FXCollections.observableArrayList(personService.getDoingPeopleIdName());
        prName.setItems(prList);
        name.setItems(personList);
    }

    public int workDays(LocalDate date1, LocalDate date2) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date11 = df.parse(String.valueOf(date1));
        Date date22 = df.parse(String.valueOf(date2));
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date11);
        cal2.setTime(date22);
        cal2.add(Calendar.DATE, 1);
        int numberOfDays = 0;
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            cal1.add(Calendar.DATE, 1);
        }
        return numberOfDays;
    }
    //TODO: event handler
    public void onPickStart(ActionEvent e) {
        Callback<DatePicker, DateCell> callback = new Callback<>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        setDisable(empty || item.compareTo(startDate.getValue()) < 0);
                    }

                };
            }
        };
        deadline.setDayCellFactory(callback);
        finishDate.setDayCellFactory(callback);
    }

    public void onPickDeadline(ActionEvent e) throws ParseException {
        LocalDate date1 = startDate.getValue();
        LocalDate date2 = deadline.getValue();
        if (date2.compareTo(date1) < 0) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.ERROR_DEADLINE_BEFORE_START_DATE);
        } else {
            expectedTime.setText(String.valueOf(workDays(date1, date2)));
        }
    }

    public void onPickFinishDate(ActionEvent e) throws ParseException {
        LocalDate date1 = startDate.getValue();
        LocalDate date2 = finishDate.getValue();
        if (date2.compareTo(date1) < 0) {
            _Alert.showWaitInfoNotification(Constant.DialogConstant.ERROR_FINISH_TIME_BEFORE_START_DATE);
        } else {
            finishTime.setText(String.valueOf(workDays(date1, date2)));
        }
    }

    public void AddProject(ActionEvent e) throws IOException {
        modalWindowController.showWindowModal(e,
                Constant.FXMLPage.ADD_PROJECT,
                Constant.WindowTitleConstant.ADD_PROJECT_TITLE);
        setComboBox();
    }

    public void AddPerson(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        FXMLLoader loader = modalWindowController.getLoader(Constant.FXMLPage.ADD_PERSON);
        Parent parent = modalWindowController.load(loader);

        modalWindowController.showWindowModal(node, parent,
                Constant.WindowTitleConstant.ADD_PERSON_TITLE);
        setComboBox();
        AddPerson addPerson = loader.getController();
        addPerson.setID();
    }

    public void cancel(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void submit(ActionEvent e) {
        if (validate()) {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            taskService.addTask(getTask());
            _Alert.showWaitInfoNotification(Constant.DialogConstant.SUCCESS_ADD_TASK);
            stage.close();
        }
    }
}
